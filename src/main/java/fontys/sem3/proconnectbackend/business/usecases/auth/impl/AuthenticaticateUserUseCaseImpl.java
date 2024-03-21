package fontys.sem3.proconnectbackend.business.usecases.auth.impl;

import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.converters.ExpertConverter;
import fontys.sem3.proconnectbackend.business.dtos.AuthenticationRequest;
import fontys.sem3.proconnectbackend.business.dtos.LoginResponse;
import fontys.sem3.proconnectbackend.business.usecases.auth.AuthenticateUserUseCase;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticaticateUserUseCaseImpl implements AuthenticateUserUseCase {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     *
     * @param request contains all required data authenticate
     * @should throw exception when request data is not valid
     * @should return the response if credentials are correct
     */
    @Override
    public LoginResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).map(userEntity -> {
            if (userEntity instanceof ClientEntity clientEntity) {
                return ClientConverter.convert(clientEntity);
            } else if (userEntity instanceof ExpertEntity expertEntity) {
                return ExpertConverter.convert(expertEntity);
            }

            return null;
        }).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
