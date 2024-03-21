package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAlreadyExistsExceptionTest {
    @Test
    public void testInvalidTokenException() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("EMAIL_ALREADY_EXISTS");
    }
}
