package fontys.sem3.proconnectbackend.domain;

import fontys.sem3.proconnectbackend.domain.enums.Role;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Expert extends User {
    @Length(max = 500)
    private String bio;
    private int likes;
    private int dislikes;
    @Length(max = 50)
    private String profession;
    @Max(50)
    private int yearsOfExperience;

    public Expert(Long id,
                        String email,
                        String password,
                        String firstName,
                        String lastName,
                        String phone,
                        String city,
                        String address,
                        String profileImageUrl,
                        Role role
    ) {
        super(id, email, password, firstName, lastName, phone, city, address, profileImageUrl, role);
        this.likes = 0;
        this.dislikes = 0;
        this.role = Role.ROLE_Expert;
    }
}