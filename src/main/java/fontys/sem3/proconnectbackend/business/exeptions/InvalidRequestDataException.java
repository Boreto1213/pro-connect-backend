package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestDataException extends ResponseStatusException {
    public InvalidRequestDataException() {super(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_DATA");}
}
