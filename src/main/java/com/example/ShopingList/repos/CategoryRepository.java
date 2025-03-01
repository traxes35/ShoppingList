package com.example.ShopingList.repos;

import com.example.ShopingList.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
    boolean existsByName(String name);
}
