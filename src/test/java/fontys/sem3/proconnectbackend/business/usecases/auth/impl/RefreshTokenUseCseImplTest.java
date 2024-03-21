package fontys.sem3.proconnectbackend.business.usecases.auth.impl;

import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.converters.ExpertConverter;
import fontys.sem3.proconnectbackend.business.dtos.AuthenticationResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidAuthHeadersException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidTokenException;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.domain.Client;
import fontys.sem3.proconnectbackend.domain.Expert;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RefreshTokenUseCseImplTest {
    /**
     * @verifies throw InvalidAuthHeadersException when there is no auth header
     * @see RefreshTokenUseCseImpl#refreshToken(jakarta.servlet.http.HttpServletRequest)
     */


    /**
     * @verifies throw UsernameNotFoundException if credentials are wrong
     * @see RefreshTokenUseCseImpl#refreshToken(jakarta.servlet.http.HttpServletRequest)
     */
    @Test
    public void refreshToken_shouldThrowUsernameNotFoundExceptionIfCredentialsAreWrong() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", "mock-refresh-token-value");
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { refreshTokenCookie });
        when(jwtService.extractId("mock-refresh-token-value")).thenReturn("1");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // Act
        RefreshTokenUseCseImpl refreshTokenUseCse = new RefreshTokenUseCseImpl(jwtService, userRepository);
        // Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            refreshTokenUseCse.refreshToken(mockRequest);
        });
    }

    /**
     * @verifies throw InvalidTokenException if token is invalid
     * @see RefreshTokenUseCseImpl#refreshToken(jakarta.servlet.http.HttpServletRequest)
     */
    @Test
    public void refreshToken_shouldThrowInvalidTokenExceptionIfTokenIsInvalid() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", "mock-refresh-token-value");
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { refreshTokenCookie });
        when(jwtService.extractId("mock-refresh-token-value")).thenReturn(null);
        // Act
        RefreshTokenUseCseImpl refreshTokenUseCse = new RefreshTokenUseCseImpl(jwtService, userRepository);
        // Assert
        assertThrows(InvalidTokenException.class, () -> {
            refreshTokenUseCse.refreshToken(mockRequest);
        });
    }

    /**
     * @verifies return the response if token is valid
     * @see RefreshTokenUseCseImpl#refreshToken(jakarta.servlet.http.HttpServletRequest)
     */
    @Test
    public void refreshToken_shouldReturnTheResponseIfTokenIsValidWithClient() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", "mock-refresh-token-value");
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { refreshTokenCookie });
        when(jwtService.extractId("mock-refresh-token-value")).thenReturn("1");

        ClientEntity clientEntity = ClientEntity.builder()
                .email("asd@gmail.com")
                .password("asdASD123@")
                .id(1L)
                .address("Some address")
                .city("Eindhoven")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+3111111111")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        Client client = ClientConverter.convert(clientEntity);

        when(jwtService.isTokenValid("mock-refresh-token-value", client)).thenReturn(true);
        when(jwtService.generateRefreshToken(client)).thenReturn("asd123$");
        // Act
        RefreshTokenUseCseImpl refreshTokenUseCse = new RefreshTokenUseCseImpl(jwtService, userRepository);
        AuthenticationResponse response =  refreshTokenUseCse.refreshToken(mockRequest);
        // Assert
        assertEquals("asd123$", response.getAccessToken());
    }

    @Test
    public void refreshToken_shouldReturnTheResponseIfTokenIsValidWithExpert() throws Exception {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", "mock-refresh-token-value");
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { refreshTokenCookie });
        when(jwtService.extractId("mock-refresh-token-value")).thenReturn("1");
        ExpertEntity expertEntity = ExpertEntity.builder()
                .email("asd@gmail.com")
                .password("asdASD123@")
                .id(1L)
                .address("Some address")
                .city("Eindhoven")
                .firstName("Alex")
                .lastName("Getov")
                .phone("+3111111111")
                .build();
        Expert expert = ExpertConverter.convert(expertEntity);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(expertEntity));
        when(jwtService.isTokenValid("mock-refresh-token-value", expert)).thenReturn(true);
        when(jwtService.generateRefreshToken(expert)).thenReturn("asd123$");
        // Act
        RefreshTokenUseCseImpl refreshTokenUseCse = new RefreshTokenUseCseImpl(jwtService, userRepository);
        AuthenticationResponse response =  refreshTokenUseCse.refreshToken(mockRequest);
        // Assert
        assertEquals("asd123$", response.getAccessToken());
    }
}
