package fontys.sem3.proconnectbackend.configuration.security;

import fontys.sem3.proconnectbackend.business.usecases.user.GetUserByIdUseCase;
import fontys.sem3.proconnectbackend.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final GetUserByIdUseCase getUserByIdUseCase;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessTokenCookie")) {
                jwt = cookie.getValue();
            }
        }

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        final String userId = jwtService.extractId(jwt);
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userId;
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwt = authHeader.substring(7);
//        userId = jwtService.extractId(jwt);
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = getUserByIdUseCase.getUserById(Long.parseLong(userId));
            if(user.isPresent() && jwtService.isTokenValid(jwt, user.get())) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.get().getEmail());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails((request))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
