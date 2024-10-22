package com.mealKit.backend.repository;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Integer> {
    List<Ordering> findByUser(User user);

}
