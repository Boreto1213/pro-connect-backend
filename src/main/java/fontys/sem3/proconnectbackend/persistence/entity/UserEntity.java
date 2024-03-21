package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;
    @Column(name = "email", length = 30, nullable = false)
    @Email
    protected String email;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "first_name", length = 30, nullable = false)
    @NotEmpty
    protected String firstName;
    @Column(name = "last_name", length = 30, nullable = false)
    @NotEmpty
    protected String lastName;
    @Column(name = "phone", length = 13, nullable = false)
    @Pattern(regexp = "^\\+[\\d]{1,3}[\\d]{9}$")
    protected String phone;
    @Column(name = "city", length = 30, nullable = false)
    @NotEmpty
    protected String city;
    @Column(name = "address", length = 30, nullable = false)
    @NotEmpty
    protected String address;
    @Column(name = "profile_image_url", nullable = false)
    protected String profileImageUrl;
}



