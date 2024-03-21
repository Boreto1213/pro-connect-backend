package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidGoogleAuthException extends ResponseStatusException {
    public InvalidGoogleAuthException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_GOOGLE_AUTH");
    }
}
