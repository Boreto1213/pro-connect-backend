package fontys.sem3.proconnectbackend.persistence;

import fontys.sem3.proconnectbackend.persistence.entity.MessageEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    ArrayList<MessageEntity> findBySender(UserEntity userEntity);
    ArrayList<MessageEntity> findByReceiver(UserEntity userEntity);
    @Query("SELECT m FROM MessageEntity m WHERE " +
            "(m.sender.id = ?1 AND m.receiver.id = ?2) OR " +
            "(m.sender.id = ?2 AND m.receiver.id = ?1) " +
            "ORDER BY m.timestamp DESC")
    Page<MessageEntity> findLastMessageBetweenUsers(Long userId1, Long userId2, Pageable pageable);

    @Query("SELECT m FROM MessageEntity m WHERE " +
            "(m.sender.id = ?1 AND m.receiver.id = ?2) OR " +
            "(m.sender.id = ?2 AND m.receiver.id = ?1) " +
            "ORDER BY m.timestamp")
    List<MessageEntity> getChatHistory(Long userId1, Long userId2);

//    @Query("SELECT m FROM MessageEntity m WHERE " +
//            "(m.sender.id = ?1) OR " +
//            "(m.receiver.id = ?1)" +
//            "ORDER BY m.timestamp DESC")
//    ArrayList<MessageEntity> findMessagesByUserId(Long userId);
}

