package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DataOwnershipViolationException extends ResponseStatusException {
    public DataOwnershipViolationException() {
        super(HttpStatus.BAD_REQUEST, "CANNOT_MODIFY_RESOURCE");
    }
}
