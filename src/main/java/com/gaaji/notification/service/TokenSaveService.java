package com.gaaji.notification.service;


import com.gaaji.notification.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenSaveService {
    private final TokenRepository tokenRepository;

    public void saveFcmToken(String userId, String fcmToken){
        tokenRepository.write(userId, fcmToken);
    }


}
