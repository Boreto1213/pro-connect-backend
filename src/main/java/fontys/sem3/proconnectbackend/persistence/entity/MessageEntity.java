package fontys.sem3.proconnectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity sender;
    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity receiver;
    @Column(name = "text", length = 1000, nullable = false)
    private String text;
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
}
