package com.example.ShopingList.repos;


import com.example.ShopingList.entities.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

    // shoppingItemId ile ShoppingItem'ı sorgula
    Optional<ShoppingItem> findById(Long id);
    // shoppingListId'ye göre ShoppingItem'ları listele
    List<ShoppingItem> findByShoppingListId(Long shoppingListId);
    List<ShoppingItem> findByShoppingList_UserId(Long userId);
    List<ShoppingItem> findByUserId(Long userId);

}