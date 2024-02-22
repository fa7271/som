package com.encore.post.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void redisConnectionTest() throws JsonProcessingException {
        final String key = "postid"+"_"+"d";
        final Long userId = 123L;
        final LocalDateTime timestamp = LocalDateTime.now();

        // Map을 사용하여 데이터를 구성
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userId", userId);
        dataMap.put("timestamp", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(dataMap);

        // Redis에 데이터 저장
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, jsonData);

        // Redis에서 데이터 가져오기
        final String retrievedData = valueOperations.get(key);

        // JSON 문자열을 다시 Map으로 변환
        Map<String, Object> retrievedMap = objectMapper.readValue(retrievedData, Map.class);

        // 값 확인
        Assertions.assertThat(dataMap.get("userId")).isEqualTo( retrievedMap.get("userId"));
        Assertions.assertThat(dataMap.get("timestamp")).isEqualTo( retrievedMap.get("timestamp"));
    }
}
