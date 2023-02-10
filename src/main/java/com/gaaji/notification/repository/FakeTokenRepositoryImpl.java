package com.gaaji.notification.repository;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Profile("redis-fake")
@Transactional
@Repository
public class FakeTokenRepositoryImpl implements TokenRepository{

    private static Map<String,String> storage = new ConcurrentHashMap<>();
    @Override
    public void write(String key, String value) {
        storage.put(key,value);
    }

    @Override
    public String read(String key) {
        String value = storage.get(key);
        Assert.notNull(value,"Key에 해당하는 값이 존재하지 않습니다.");
        return value;
    }
}
