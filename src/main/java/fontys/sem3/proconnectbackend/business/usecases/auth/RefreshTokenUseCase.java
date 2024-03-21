package fontys.sem3.proconnectbackend.business.usecases.auth;

import fontys.sem3.proconnectbackend.business.dtos.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenUseCase {
    public AuthenticationResponse refreshToken(HttpServletRequest request);
}
