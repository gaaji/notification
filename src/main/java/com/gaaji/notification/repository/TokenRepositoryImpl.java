package com.gaaji.notification.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Profile({"redis-local","redis-docker"})
@RequiredArgsConstructor
@Transactional
@Repository
public class TokenRepositoryImpl implements TokenRepository{

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void write(String key, String value) {

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key,value);

    }

    @Override
    public String read(String key) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value = ops.get(key);
        Assert.notNull(value,"Key에 해당하는 값이 존재하지 않습니다.");
        return value;
    }
}
