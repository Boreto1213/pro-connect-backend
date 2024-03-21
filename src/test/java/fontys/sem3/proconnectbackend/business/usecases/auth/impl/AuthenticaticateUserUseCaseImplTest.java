package fontys.sem3.proconnectbackend.business.usecases.auth.impl;

import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.dtos.AuthenticationRequest;
import fontys.sem3.proconnectbackend.business.dtos.LoginResponse;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.domain.Client;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticaticateUserUseCaseImplTest {
    /**
     * @verifies throw exception when request data is not valid
     * @see AuthenticaticateUserUseCaseImpl#authenticate(fontys.sem3.proconnectbackend.business.dtos.AuthenticationRequest)
     */
    @Test
    public void authenticate_shouldThrowExceptionWhenRequestDataIsNotValid() throws Exception {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        AuthenticationManager  authenticationManager = mock(AuthenticationManager.class);
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("Something went wrong.") {
        });

        // Act
        AuthenticaticateUserUseCaseImpl authenticaticateUserUseCase = new AuthenticaticateUserUseCaseImpl(userRepository, jwtService, authenticationManager);
        // Assert
        assertThrows(AuthenticationException.class, () -> {
            authenticaticateUserUseCase.authenticate(new AuthenticationRequest(email, password));
        });
    }

    /**
     * @verifies return the response if credentials are correct
     * @see AuthenticaticateUserUseCaseImpl#authenticate(fontys.sem3.proconnectbackend.business.dtos.AuthenticationRequest)
     */
    @Test
    public void authenticate_shouldReturnTheResponseIfCredentialsAreCorrect() throws Exception {
        String email = "test@example.com";
        String password = "password";
        AuthenticationManager  authenticationManager = mock(AuthenticationManager.class);
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);

        // Mock the authentication manager
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        // Mock the userRepository to return a user
        ClientEntity clientEntity = ClientEntity.builder()
                .email(email)
                .password(password)
                .id(1L)
                .address("Some address")
                .city("Eindhoven")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+3111111111")
                .build();
        Client client = ClientConverter.convert(clientEntity);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", client.getRole());
        when(jwtService.generateToken(extraClaims ,client)).thenReturn("asd123$");
        when(jwtService.generateRefreshToken(client)).thenReturn("123asd123$");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(clientEntity));

        // Call the authenticate method
        AuthenticaticateUserUseCaseImpl authenticaticateUserUseCase = new AuthenticaticateUserUseCaseImpl(userRepository, jwtService, authenticationManager);
        LoginResponse response = authenticaticateUserUseCase.authenticate(new AuthenticationRequest(email, password));

        // Assertions
        assertEquals("asd123$", response.getAccessToken());
        assertEquals("123asd123$", response.getRefreshToken());
    }
}
