package com.mealKit.backend.redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisConfig redisTemplate;

    // 데이터 저장
    public void save(String key, String value) {
        redisTemplate.redisTemplate().opsForValue().set(key, value);
    }

    // 데이터 조회
    public Object findByKey(String key) {
        return redisTemplate.redisTemplate().opsForValue().get(key);
    }

    // 데이터 삭제
    public void delete(String key) {
        redisTemplate.redisTemplate().delete(key);
    }
}
