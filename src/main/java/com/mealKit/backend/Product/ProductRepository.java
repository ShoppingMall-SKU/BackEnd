package com.mealKit.backend.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByBrand(String brand);

}
