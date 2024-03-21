package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserIdException extends ResponseStatusException {
    public InvalidUserIdException() {super(HttpStatus.BAD_REQUEST, "EXPERT_ID_DOES_NOT_EXIST");}
}
