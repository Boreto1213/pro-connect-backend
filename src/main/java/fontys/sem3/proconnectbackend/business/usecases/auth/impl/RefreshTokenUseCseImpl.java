package fontys.sem3.proconnectbackend.business.usecases.auth.impl;

import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.converters.ExpertConverter;
import fontys.sem3.proconnectbackend.business.dtos.AuthenticationResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidAuthHeadersException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidTokenException;
import fontys.sem3.proconnectbackend.business.usecases.auth.RefreshTokenUseCase;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCseImpl  implements RefreshTokenUseCase {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     *
     * @param request HttpServletRequest
     * @should throw InvalidAuthHeadersException when there is no auth header
     * @should throw UsernameNotFoundException if credentials are wrong
     * @should throw InvalidTokenException if token is invalid
     * @should return the response if token is valid
     */
    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request) throws UsernameNotFoundException, InvalidTokenException, InvalidAuthHeadersException  {
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshTokenCookie")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new InvalidAuthHeadersException();

        }

//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userId;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new InvalidAuthHeadersException();
//        }
//        refreshToken = authHeader.substring(7);
//        userId = jwtService.extractId(refreshToken);
        final String userId = jwtService.extractId(refreshToken);
        if(userId != null) {
            User user = userRepository.findById(Long.parseLong(userId)).map(userEntity -> {
                if (userEntity instanceof ClientEntity clientEntity) {
                    return ClientConverter.convert(clientEntity);
                }
                else if (userEntity instanceof ExpertEntity expertEntity) {
                    return ExpertConverter.convert(expertEntity);
                }

                return null;
            }).orElseThrow(
                () -> new UsernameNotFoundException("Email not found!")
            );
            if (jwtService.isTokenValid(refreshToken, user)) {
                // TODO... change to generate access token
                String accessToken =jwtService.generateRefreshToken(user);
                var response = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                return response;
            }
        }

        throw new InvalidTokenException();
    }
}
