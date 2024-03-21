package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.ServiceIdDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceIdDoesNotExistExceptionTest {
    @Test
    public void testInvalidTokenException() {
        ServiceIdDoesNotExistException exception = new ServiceIdDoesNotExistException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("SERVICE_ID_DOES_NOT_EXIST");
    }
}
