package fontys.sem3.proconnectbackend.business.usecases.auth;

import fontys.sem3.proconnectbackend.business.dtos.AuthenticationRequest;
import fontys.sem3.proconnectbackend.business.dtos.LoginResponse;

public interface AuthenticateUserUseCase {
    public LoginResponse authenticate(AuthenticationRequest request);
}
