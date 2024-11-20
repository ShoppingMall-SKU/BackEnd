package com.mealKit.backend.redisson;

import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final ProductService productService;

    /**
     * 동시성 제어해서 수량 감소 시키는 메소드
     * @param id User 아이디
     * @param quantity 주문 수량
     * @throws InterruptedException
     */
    public Boolean decrease(Integer id, Integer quantity) throws InterruptedException {
        RLock lock = redissonClient.getLock(id.toString());

        try {
            // 15초동안 재획득 시도, 1초안에 해제
            boolean isAvailable = lock.tryLock(15, 1, TimeUnit.SECONDS);
            if(!isAvailable) {
                log.error("Lock fail");
                return false;
            }
            productService.modifyStock(id, quantity);

        } catch (InterruptedException e) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        } finally {
            lock.unlock();
        }

        return true;
    }


}
