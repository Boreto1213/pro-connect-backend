package fontys.sem3.proconnectbackend.business.usecases.auth;

import fontys.sem3.proconnectbackend.business.dtos.AuthenticationCodeRequest;
import fontys.sem3.proconnectbackend.business.dtos.LoginResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidGoogleAuthException;
import org.apache.http.auth.InvalidCredentialsException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthenticateWithGoogleUseCase {
    LoginResponse authenticate(AuthenticationCodeRequest request) throws IOException, GeneralSecurityException, InvalidCredentialsException, InvalidGoogleAuthException;
}
