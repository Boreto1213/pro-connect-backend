package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", length = 100)
    private String title;
    @JoinColumn(name = "expert_id")
    @ManyToOne(targetEntity = ExpertEntity.class)
    private ExpertEntity expert;
    @Column(name = "description", length = 1000)
    @NotEmpty
    private String description;
    @Column(name = "price")
    @NotNull
    @Min(0)
    private BigDecimal price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "service_tags",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;
}
