package com.mealKit.backend.repository;


import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);

    Optional<User> findByPid(String pid);

    Optional<User> findByRefreshToken(String refreshToken);


}
