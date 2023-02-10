package com.gaaji.notification.repository;



public interface TokenRepository {

    void write(String key, String value);
    String read(String key);
}
