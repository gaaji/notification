package com.gaaji.notification.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.notification.dto.ChatMessage;
import com.gaaji.notification.dto.ServerPushMessage;
import com.gaaji.notification.service.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaConsumerImpl implements KafkaConsumer {

    private final SendMessageService sendMessageService;

    @KafkaListener(topics = "noti-server")
    @Override
    public void consumeServerPushMessage(String serverPushMessage) {
        try {
            ServerPushMessage message = new ObjectMapper().readValue(serverPushMessage,
                    ServerPushMessage.class);
            sendMessageService.sendServerMessage(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "noti-chat")
    @Override
    public void consumeChatMessage(String chatMessage) {
        try {
            ChatMessage message = new ObjectMapper().readValue(chatMessage,
                    ChatMessage.class);
            sendMessageService.sendChatMessage(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
