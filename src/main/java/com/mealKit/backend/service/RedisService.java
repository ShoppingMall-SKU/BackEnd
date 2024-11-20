package com.mealKit.backend.service;

import com.mealKit.backend.config.RedisConfig;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
        String val = redisTemplate.redisTemplate().opsForValue().get(key);
        if(val != null) {
            return val;
        } else {
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        }
    }

    // 데이터 삭제
    public void delete(String key) {
        redisTemplate.redisTemplate().delete(key);
    }
}
