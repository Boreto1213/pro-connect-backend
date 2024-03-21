package fontys.sem3.proconnectbackend.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Service {
    @NotNull
    private Long id;
    @NotEmpty
    @Length(max = 100)
    private String title;
    @NotNull
    private Expert expert;
    @NotEmpty
    @Length(max = 1000)
    private String description;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    private List<Tag> tags;
}
