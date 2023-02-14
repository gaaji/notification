package com.gaaji.notification.service;

import com.gaaji.notification.adaptor.FcmSender;
import com.gaaji.notification.dto.ChatMessage;
import com.gaaji.notification.dto.ServerPushMessage;
import com.gaaji.notification.repository.TokenRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SendMessageService {

    private final TokenRepository tokenRepository;
    private final FcmSender fcmSender;


    public void sendServerMessage(ServerPushMessage dto){
        List<String> tokens = dto.users().stream().map(tokenRepository::read)
                .collect(Collectors.toList());
        fcmSender.sendServerMessage(tokens,dto.message());
    }

    public void sendChatMessage(ChatMessage chatMessage){
        chatMessage.receivers().stream().forEach(receiver -> {
            String token = tokenRepository.read(receiver.id());
            fcmSender.sendChatMessage(chatMessage.senderId(), chatMessage.senderName(), token, receiver.roomName(), receiver.id(),
                    chatMessage.message());
        });
    }

}
