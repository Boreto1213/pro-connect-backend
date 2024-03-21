package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "text", length = 1000, nullable = false)
    @NotEmpty
    private String text;
    @Column(name = "rating", nullable = false)
    @Max(5)
    @Min(0)
    private BigDecimal rating;
    @JoinColumn(name = "service_id", nullable = false)
    @ManyToOne(targetEntity = ServiceEntity.class)
    private ServiceEntity service;
    @JoinColumn(name = "created_by", nullable = false)
    @ManyToOne(targetEntity = ClientEntity.class)
    private ClientEntity createdBy;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
