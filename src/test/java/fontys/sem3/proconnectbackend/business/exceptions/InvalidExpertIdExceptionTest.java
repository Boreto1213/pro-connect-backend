package fontys.sem3.proconnectbackend.business.exceptions;

import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidExpertIdExceptionTest {
    @Test
    public void testInvalidTokenException() {
        InvalidUserIdException exception = new InvalidUserIdException();

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("EXPERT_ID_DOES_NOT_EXIST");
    }
}
