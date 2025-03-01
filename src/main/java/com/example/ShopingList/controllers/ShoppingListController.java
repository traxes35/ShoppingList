package com.example.ShopingList.controllers;

import com.example.ShopingList.requests.CreateShoppingListRequest;
import com.example.ShopingList.services.ShoppingListService;
import com.example.ShopingList.entities.ShoppingList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = shoppingListService.getAllShoppingLists();
        if (shoppingLists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shoppingLists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable("id") Long id) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(id);
        if (shoppingList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingList> getShoppingListByUserId(@PathVariable("userId") Long userId) {
        shoppingListService.getShoppingListById(userId);
        return new ResponseEntity<>(shoppingListService.getShoppingListById(userId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ShoppingList> addShoppingList(@RequestBody CreateShoppingListRequest shoppingListRequest) {
        // Eğer shoppingListRequest null ise, geçersiz istek gönderiyoruz
        if (shoppingListRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Yeni ShoppingList'i ekliyoruz
        shoppingListService.addShoppingList(shoppingListRequest);

        // Başarıyla eklediğimizde, HTTP 201 (Created) döndürüyoruz
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingList shoppingList) {
        if (shoppingList == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        shoppingListService.updateShoppingList(id, shoppingList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}