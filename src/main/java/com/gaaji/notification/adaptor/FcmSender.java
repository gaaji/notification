package com.gaaji.notification.adaptor;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmSender {


    private final Environment env;
    private final String FCM_PRIVATE_KEY_PATH;
    private final String FIRE_BASE_SCOPE;


    public FcmSender(Environment env) {
        this.env = env;
        FCM_PRIVATE_KEY_PATH = env.getProperty("fcm.key.path");
        FIRE_BASE_SCOPE = env.getProperty("fcm.key.scope");
    }

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                                    .createScoped(List.of(FIRE_BASE_SCOPE)))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            // spring 뜰때 알림 서버가 잘 동작하지 않는 것이므로 바로 죽임
            throw new RuntimeException(e.getMessage());
        }
    }


    public void sendServerMessage(List<String> tokenList,  String body) {
        sendByTokenList(tokenList,"가지마켓",body);
    }
    public void sendChatMessage(String senderId, String senderName, String token, String roomName, String roomId, String message){
        Message messages = Message.builder()
                .putData("img", "https://todoay-picture.s3.ap-northeast-2.amazonaws.com/gaaji/fd308fa8-f9c1-4e2c-8a35-4c0816b51f23gajji.png")
                .putData("time", LocalDateTime.now().toString())
                .putData("roomId",roomId)
                .putData("senderId", senderId)
                .setNotification(new Notification(roomName, senderName + " : " + message))
                .setToken(token)
                .build();
        try {

            FirebaseMessaging.getInstance().send(messages);
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to memberList push message. error info : {}", e.getMessage());
        }
    }

    // 알림 보내기
    private void sendByTokenList(List<String> tokenList,  String title, String body) {
        // 메시지 만들기
        List<Message> messages = tokenList.stream().map(token -> Message.builder()
                .putData("img", "https://todoay-picture.s3.ap-northeast-2.amazonaws.com/gaaji/fd308fa8-f9c1-4e2c-8a35-4c0816b51f23gajji.png")
                .putData("time", LocalDateTime.now().toString())
                .setNotification(new Notification(title, body))
                .setToken(token)
                .build()).collect(Collectors.toList());

        // 요청에 대한 응답을 받을 response
        BatchResponse response;
        try {

            // 알림 발송
            response = FirebaseMessaging.getInstance().sendAll(messages);

            // 요청에 대한 응답 처리
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(tokenList.get(i));
                    }
                }
                log.error("List of tokens are not valid FCM token : " + failedTokens);
            }
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to memberList push message. error info : {}", e.getMessage());
        }
    }



}
