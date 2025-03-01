package com.example.ShopingList.controllers;


import com.example.ShopingList.requests.CreateShoppingItemRequest;
import com.example.ShopingList.services.ShoppingItemService;
import com.example.ShopingList.entities.ShoppingItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-items")
public class ShoppingItemController {

    private final ShoppingItemService shoppingItemService;

    public ShoppingItemController(ShoppingItemService shoppingItemService) {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingItem>> getAllShoppingItems() {
        List<ShoppingItem> shoppingItems = shoppingItemService.getAllShoppingItems();
        if (shoppingItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shoppingItems, HttpStatus.OK);
    }

    // Handle get by item ID
    @GetMapping("/item/{id}")
    public ResponseEntity<ShoppingItem> getShoppingItemById(@PathVariable("id") Long id) {
        ShoppingItem shoppingItem = shoppingItemService.getShoppingItemById(id);
        if (shoppingItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingItem, HttpStatus.OK);
    }

    // Handle get by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShoppingItem>> getShoppingItemsByUserId(@PathVariable Long userId) {
        List<ShoppingItem> shoppingItems = shoppingItemService.getShoppingItemsByUserId(userId);
        if (shoppingItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shoppingItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShoppingItem> addShoppingItem(@RequestBody CreateShoppingItemRequest shoppingItem) {
        if (shoppingItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Handle bad request better
        }

        try {
            shoppingItemService.addShoppingItem(shoppingItem);
            return new ResponseEntity<>(HttpStatus.CREATED);  // Return 201 when created successfully
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 when error occurs
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingItem(@PathVariable Long id) {
        shoppingItemService.deleteShoppingItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}