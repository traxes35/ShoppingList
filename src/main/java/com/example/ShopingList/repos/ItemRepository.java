package com.example.ShopingList.repos;

import com.example.ShopingList.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // ID'ye göre Item sorgulama (Bu, JpaRepository tarafından otomatik olarak sağlanır)
    Optional<Item> findById(Long id);

    Item getItemById(Long id);
}