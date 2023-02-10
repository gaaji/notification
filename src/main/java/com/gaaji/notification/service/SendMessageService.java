package com.gaaji.notification.service;

import com.gaaji.notification.adaptor.FcmSender;
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


    public void sendMessage(ServerPushMessage dto){
        List<String> tokens = dto.users().stream().map(u -> tokenRepository.read(u))
                .collect(Collectors.toList());
        fcmSender.sendServerMessage(tokens,dto.type().getBody());
    }

}
