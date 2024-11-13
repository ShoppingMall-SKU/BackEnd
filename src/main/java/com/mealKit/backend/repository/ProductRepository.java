package com.mealKit.backend.repository;

import com.mealKit.backend.domain.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByBrand(String brand);
    Page<Product> findAll(Pageable pageable);


    Optional<Product> findById(Integer id);


    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %?1% ORDER BY p.createDate DESC")
    Page<Product> search(String query, Pageable pageable);


}
