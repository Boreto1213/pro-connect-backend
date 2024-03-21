package fontys.sem3.proconnectbackend.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @NotNull
    private Long id;
    @NotEmpty
    @Length(max = 50)
    private String text;
}
