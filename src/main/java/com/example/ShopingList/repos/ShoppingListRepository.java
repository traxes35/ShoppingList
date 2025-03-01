package com.example.ShopingList.repos;


import com.example.ShopingList.entities.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    // User'a göre ShoppingList'leri sorgula
    Optional<ShoppingList> findByUserId(Long userId);
    // ID'ye göre ShoppingList'i sorgula
    Optional<ShoppingList> findById(Long id);
    Optional<ShoppingList> findByShoppingItems_Id(Long shoppingItemId);
}