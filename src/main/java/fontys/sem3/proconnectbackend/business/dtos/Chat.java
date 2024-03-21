package fontys.sem3.proconnectbackend.business.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @NotNull
    private Long recipientId;
    @NotEmpty
    private String recipientName;
    private String recipientProfileImageUrl;
    @NotEmpty
    private String lastMessage;
    @NotNull
    private Date lastMessageTimestamp;
}
