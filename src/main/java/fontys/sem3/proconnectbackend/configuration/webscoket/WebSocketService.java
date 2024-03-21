package fontys.sem3.proconnectbackend.configuration.webscoket;

import fontys.sem3.proconnectbackend.business.converters.MessageConverter;
import fontys.sem3.proconnectbackend.business.dtos.Chat;
import fontys.sem3.proconnectbackend.business.dtos.GetChatOptionsResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.domain.Message;
import fontys.sem3.proconnectbackend.persistence.MessageRepository;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.MessageEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getChatHistory(Long userId1, Long userId2) {
        return messageRepository.getChatHistory(userId1, userId2).stream().map(MessageConverter::convert).toList();
    }

    @Transactional
    public GetChatOptionsResponse getChatOptions(Long userId) throws InvalidUserIdException {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new InvalidUserIdException();
        }
        List<Chat> chats = new ArrayList<>();

        ArrayList<MessageEntity> sentMessages = messageRepository.findBySender(userEntityOptional.get());
        List<UserEntity> users1 = sentMessages.stream().map(MessageEntity::getReceiver).toList();

        ArrayList<MessageEntity> receivedMessages = messageRepository.findByReceiver(userEntityOptional.get());
        List<UserEntity> users2 = receivedMessages.stream().map(MessageEntity::getSender).toList();

        List<UserEntity> allUsers = new ArrayList<UserEntity>(users1);
        allUsers.addAll(users2);

        Set<UserEntity> uniqueUsers = new HashSet<>(allUsers);

        for (UserEntity userEntity : uniqueUsers) {
            Page<MessageEntity> result = messageRepository.findLastMessageBetweenUsers(userId, userEntity.getId(), PageRequest.of(0, 1));
            MessageEntity lastMessage = result.hasContent() ? result.getContent().get(0) : null;

            chats.add(Chat.builder()
                    .lastMessage(lastMessage.getText())
                    .lastMessageTimestamp(lastMessage.getTimestamp())
                    .recipientId(userEntity.getId())
                    .recipientName(userEntity.getFirstName() + " " + userEntity.getLastName())
                    .recipientProfileImageUrl(userEntity.getProfileImageUrl())
                    .build());
        }

        return GetChatOptionsResponse.builder().chats(chats).build();
    }

    public void sendDirectMessage(Message message) {
        Optional<UserEntity> receiverEntity = userRepository.findById(message.getReceiverId());
        Optional<UserEntity> senderEntity = userRepository.findById(message.getSenderId());
        if (receiverEntity.isEmpty() || senderEntity.isEmpty()) throw new InvalidUserIdException();
        messagingTemplate.convertAndSendToUser(message.getReceiverId().toString(), "/queue/inboxmessages", message);
        messageRepository.save(MessageEntity.builder()
                .id(message.getId())
                .receiver(receiverEntity.get())
                .sender(senderEntity.get())
                .text(message.getText())
                .timestamp(message.getTimestamp())
                .build());
    }
}
