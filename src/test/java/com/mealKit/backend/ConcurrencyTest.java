package com.mealKit.backend;

import com.mealKit.backend.domain.Product;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.redis.RedissonLockStockFacade;
import com.mealKit.backend.repository.ProductRepository;
import com.mealKit.backend.service.CartService;
import com.mealKit.backend.service.OrderingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private RedissonLockStockFacade redissonLockStockFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CartService cartService;
    @Autowired
    private OrderingService orderingService;

    @Test
    void trying () throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i=0; i<threadCount; i++){
            executorService.submit(()->{
                try{
                    redissonLockStockFacade.decrease(51,1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Product stock = productRepository.findById(51).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        //100 -(1*100) = 0
        assertEquals(70, stock.getStock());
    }

    @Test
    void createCart() {
        cartService.createCart("108514609125596773019", 51, 4);
        cartService.createCart("108514609125596773019", 52, 4);
        cartService.createCart("108514609125596773019", 54, 2);
        cartService.createCart("108514609125596773019", 55, 1);

    }

    @Test
    void createOrder() throws InterruptedException {
        orderingService.create("108514609125596773019", "서울시 강동구", "홍길동", "1234");
    }

}
