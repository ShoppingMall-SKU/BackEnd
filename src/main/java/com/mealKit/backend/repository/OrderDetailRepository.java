package com.mealKit.backend.repository;

import com.mealKit.backend.domain.Ordering;
import com.mealKit.backend.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrdering(Ordering ordering);
}
