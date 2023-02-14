package com.gaaji.notification.adaptor;

import com.gaaji.notification.dto.ServerPushMessage;

public interface KafkaConsumer {

    void consumeServerPushMessage(String serverPushMessage);

    void consumeChatMessage(String chatMessage);
}
