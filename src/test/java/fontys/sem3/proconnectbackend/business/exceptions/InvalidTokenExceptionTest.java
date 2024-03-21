package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.InvalidTokenException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidTokenExceptionTest {

    @Test
    public void testInvalidTokenException() {
        InvalidTokenException exception = new InvalidTokenException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("TOKEN_IS_INVALID");
    }
}
