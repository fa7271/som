package com.encore.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    private final StringRedisTemplate template;

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    public void setDataExpire(String key, String value) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        valueOperations.set(key, value);
    }
    public void deleteData(String key) {
        template.delete(key);
    }
}
