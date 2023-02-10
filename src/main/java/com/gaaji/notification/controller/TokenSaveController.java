package com.gaaji.notification.controller;


import com.gaaji.notification.service.TokenSaveService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenSaveController {

    private final TokenSaveService tokenSaveService;


    @PostMapping("/notification")
    public ResponseEntity<Void> saveToken(@RequestBody Map<String,String> dto){
        tokenSaveService.saveFcmToken(dto.get("userId"), dto.get("fcmToken"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
