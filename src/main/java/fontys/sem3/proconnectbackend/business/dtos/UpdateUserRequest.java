package fontys.sem3.proconnectbackend.business.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateUserRequest {
    @NotNull
    private Long id;
    @Email
    private String email;
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$")
//    private String password;
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
    @Length(max = 500)
    private String bio;
    @Length(max = 50)
    private String profession;
    @Max(50)
    private int yearsOfExperience;

}
