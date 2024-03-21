package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("client")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ClientEntity extends UserEntity {
    // May be added in the future
    // private ArrayList<String> interests;

    public ClientEntity(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName,
            String phone,
            String city,
            String address,
            String profileImageUrl
    ) {
        super(id, email, password, firstName, lastName, phone, city, address, profileImageUrl);
//        interests = new ArrayList<>();
    }
}