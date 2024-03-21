package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.InvalidRequestDataException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidRequestDataExceptionTest {
    @Test
    public void testInvalidTokenException() {
        InvalidRequestDataException exception = new InvalidRequestDataException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("INVALID_REQUEST_DATA");
    }
}
