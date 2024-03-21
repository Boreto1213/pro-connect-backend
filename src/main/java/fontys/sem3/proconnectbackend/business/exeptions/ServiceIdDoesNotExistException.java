package fontys.sem3.proconnectbackend.business.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceIdDoesNotExistException extends ResponseStatusException {
    public ServiceIdDoesNotExistException() {super(HttpStatus.BAD_REQUEST, "SERVICE_ID_DOES_NOT_EXIST");}
}