package com.example.ShopingList.controllers;

import com.example.ShopingList.requests.CreateItemRequest;
import com.example.ShopingList.services.ItemService;
import com.example.ShopingList.entities.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // shoppingItemId ile sorgulama yapan metod
    @GetMapping("/shoppingItem/{shoppingItemId}")
    public ResponseEntity<List<Item>> getItemsByShoppingItemId(@PathVariable long shoppingItemId) {
        List<Item> items = itemService.getItemsInAShoppingItem(shoppingItemId);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // id ile sorgulama yapan metod
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        Item item = itemService.getOneItemsById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody CreateItemRequest item) {
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemService.addItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody CreateItemRequest item) {
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemService.addItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}