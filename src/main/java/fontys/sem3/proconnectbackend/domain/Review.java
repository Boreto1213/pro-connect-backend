package fontys.sem3.proconnectbackend.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @NotNull
    private Long id;
    @NotEmpty
    @Length(max = 1000)
    private String text;
    @NotNull
    @Max(5)
    @Min(0)
    private BigDecimal rating;
    @NotNull
    private Service service;
    @NotNull
    private Client createdBy;
    @NotNull
    private Date createdAt;
}
