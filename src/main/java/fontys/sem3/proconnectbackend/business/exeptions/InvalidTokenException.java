package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {
    public InvalidTokenException() {
        super(HttpStatus.BAD_REQUEST, "TOKEN_IS_INVALID");
    }
}
