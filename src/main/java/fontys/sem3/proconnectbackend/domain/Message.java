package fontys.sem3.proconnectbackend.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @NotNull
    private String id;
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotEmpty
    @Length(max = 1000)
    private String text;
    @NotNull
    private Date timestamp;
}
