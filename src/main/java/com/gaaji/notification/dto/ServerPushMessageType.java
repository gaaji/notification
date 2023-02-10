package com.gaaji.notification.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerPushMessageType {
    INTEREST_CHANGED("관심 상품이 변경되었어요"),
    NOTIFICATION("공지 사항을 확인해주세요"),
    REPORT("당신은 고소당했습니다.")

    ;


    private final String body;


}
