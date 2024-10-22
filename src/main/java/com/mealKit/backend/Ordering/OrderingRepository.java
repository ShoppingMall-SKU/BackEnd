package com.mealKit.backend.Ordering;

import com.mealKit.backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Integer> {
    List<Ordering> findByUser(User user);

}
