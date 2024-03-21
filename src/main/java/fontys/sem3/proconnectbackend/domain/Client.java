package fontys.sem3.proconnectbackend.domain;

import fontys.sem3.proconnectbackend.domain.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Client extends User {
    // May be added in the future
    // private ArrayList<String> interests;

    public Client(
            Long id,
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
        this.role = Role.ROLE_Client;
//        interests = new ArrayList<>();
    }
}