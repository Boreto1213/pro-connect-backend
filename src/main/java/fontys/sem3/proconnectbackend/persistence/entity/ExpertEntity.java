package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Entity
@DiscriminatorValue("expert")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ExpertEntity extends UserEntity {
    @Column(name = "bio", length = 500)
    private String bio;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;
    @Column(name = "profession", length = 50)
    private String profession;
    @Column(name = "yearsOfExperience")
    @Max(50)
    private int yearsOfExperience;

    public ExpertEntity(Long id,
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
        this.likes = 0;
        this.dislikes = 0;
    }
}


