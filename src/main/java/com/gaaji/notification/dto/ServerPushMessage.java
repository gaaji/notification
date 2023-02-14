package com.gaaji.notification.dto;


import java.util.List;


public record ServerPushMessage(List<String> users, ServerPushMessageType type) {

}
