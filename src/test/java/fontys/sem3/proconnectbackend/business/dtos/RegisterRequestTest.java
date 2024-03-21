package fontys.sem3.proconnectbackend.business.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterRequestTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testRegisterRequestValidation() {
        RegisterRequest request = RegisterRequest.builder()
                .email("invalid_email") // Provide invalid data for testing
                .password("weakpassword") // Provide invalid data for testing
                .firstName("John")
                .lastName("Doe")
                .phone("+1234567890")
                .city("City")
                .address("Address")
                .isExpert(true)
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Check if there are violations based on the constraints
        assertEquals(2, violations.size()); // Adjust based on your actual constraints

        // Assert on specific violations if needed
        // Example:
        // assertTrue(violations.stream().anyMatch(violation ->
        //        violation.getPropertyPath().toString().equals("email")));
        // assertTrue(violations.stream().anyMatch(violation ->
        //        violation.getPropertyPath().toString().equals("password")));
    }
}
