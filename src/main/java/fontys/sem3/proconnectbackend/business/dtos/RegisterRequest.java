package fontys.sem3.proconnectbackend.business.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$")
    private String password;
    @NotEmpty
    @Length(max = 30)
    private String firstName;
    @NotEmpty
    @Length(max = 30)
    private String lastName;
    @Pattern(regexp = "^\\+[\\d]{1,3}[\\d]{9}$")
    private String phone;
    @NotEmpty
    @Length(max = 30)
    private String city;
    @NotEmpty
    @Length(max = 30)
    private String address;
    @JsonProperty
    @NotNull
    private boolean isExpert;
}
