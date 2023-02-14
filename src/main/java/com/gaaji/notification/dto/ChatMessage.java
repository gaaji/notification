package com.gaaji.notification.dto;

import java.util.List;

public record ChatMessage(String senderId, String senderName, String chatRoomId, String message, List<Receiver> receivers) {

}
