package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAuthHeadersException extends ResponseStatusException {
    public InvalidAuthHeadersException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_AUTH_HEADER");
    }
}
