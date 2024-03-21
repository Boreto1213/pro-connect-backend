package fontys.sem3.proconnectbackend.business.dtos;

import fontys.sem3.proconnectbackend.domain.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceRequest {
    @NotNull
    private Long expertId;
    @NotEmpty
    @Length(max = 100)
    private String title;
    @NotEmpty
    @Length(max = 1000)
    private String description;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    private ArrayList<Tag> tags;
}
