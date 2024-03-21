package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Message;
import fontys.sem3.proconnectbackend.persistence.entity.MessageEntity;

public class MessageConverter {
    public static Message convert(MessageEntity messageEntity) {
        var message = new Message();
        message.setId(messageEntity.getId());
        message.setText(messageEntity.getText());
        message.setSenderId(messageEntity.getSender().getId());
        message.setReceiverId(messageEntity.getReceiver().getId());
        message.setTimestamp(messageEntity.getTimestamp());

        return message;
    }
}
