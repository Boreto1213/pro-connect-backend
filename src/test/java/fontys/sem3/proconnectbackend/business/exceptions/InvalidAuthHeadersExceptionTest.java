package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.InvalidAuthHeadersException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidAuthHeadersExceptionTest {
    @Test
    public void testInvalidTokenException() {
        InvalidAuthHeadersException exception = new InvalidAuthHeadersException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("INVALID_AUTH_HEADER");
    }
}
