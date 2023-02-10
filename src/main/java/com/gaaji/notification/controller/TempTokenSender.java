package com.gaaji.notification.controller;

import com.gaaji.notification.service.SendMessageService;
import com.gaaji.notification.dto.ServerPushMessage;
import com.gaaji.notification.dto.ServerPushMessageType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TempTokenSender {

    private final SendMessageService service;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody ServerPushMessage body){
        service.sendMessage(body);
        return ResponseEntity.ok().build();
    }

}
