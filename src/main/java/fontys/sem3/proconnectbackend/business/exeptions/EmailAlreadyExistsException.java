package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistsException extends ResponseStatusException {
    public EmailAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_EXISTS");
    }
}