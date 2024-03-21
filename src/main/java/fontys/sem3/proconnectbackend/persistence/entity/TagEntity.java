package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    @Column(name = "text", nullable = false, length = 50)
    // TODO (maybe?) -> make the text unique
    private String text;
}
