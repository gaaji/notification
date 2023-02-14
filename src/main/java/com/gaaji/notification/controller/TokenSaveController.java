package com.gaaji.notification.controller;


import com.gaaji.notification.dto.FcmToken;
import com.gaaji.notification.service.TokenSaveService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenSaveController {

    private final TokenSaveService tokenSaveService;


    @PostMapping("/notification")
    public ResponseEntity<Void> saveToken(@RequestHeader(HttpHeaders.AUTHORIZATION)String userId, @RequestBody FcmToken dto){
        tokenSaveService.saveFcmToken(userId, dto.token());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
