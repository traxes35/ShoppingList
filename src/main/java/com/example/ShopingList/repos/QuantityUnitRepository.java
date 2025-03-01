package com.example.ShopingList.repos;

import com.example.ShopingList.entities.QuantityUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityUnitRepository extends JpaRepository<QuantityUnit, Long> {
    // JpaRepository, otomatik olarak CRUD işlemleri için gerekli metodları sağlar
}