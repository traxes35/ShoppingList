package com.example.ShopingList.services;

import com.example.ShopingList.entities.*;
import com.example.ShopingList.repos.ItemRepository;
import com.example.ShopingList.repos.ShoppingItemRepository;
import com.example.ShopingList.repos.ShoppingListRepository;
import com.example.ShopingList.repos.UserRepository;
import com.example.ShopingList.requests.CreateShoppingItemRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;

    public ShoppingItemService(ShoppingItemRepository shoppingItemRepository, ItemRepository itemRepository, ShoppingListRepository shoppingListRepository, UserRepository userRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
    }

    // Tüm ShoppingItem'ları döndüren metod
    public List<ShoppingItem> getAllShoppingItems() {
        return shoppingItemRepository.findAll();
    }

    // ID'ye göre ShoppingItem döndüren metod
    public ShoppingItem getShoppingItemById(Long id) {
        Optional<ShoppingItem> shoppingItem = shoppingItemRepository.findById(id);
        return shoppingItem.orElse(null);  // Eğer yoksa null dönecek
    }
    public List<ShoppingItem> getShoppingItemsByUserId(Long userId) {
        return shoppingItemRepository.findByShoppingList_UserId(userId);
    }

    public void addShoppingItem(CreateShoppingItemRequest shoppingItem) {
        // Item ve User'ı veritabanından alıyoruz
        Optional<Item> item = itemRepository.findById(shoppingItem.getItemId());
        Optional<User> user = userRepository.findById(shoppingItem.getUserId());
        // ShoppingList, eğer mevcut değilse, kullanıcıyla ilişkilendirilmiş ilk alışveriş listesini alıyoruz
        Optional<ShoppingList>  shoppingList = shoppingListRepository.findById(shoppingItem.getShoppingListId());

        // ShoppingItem nesnesini oluşturup, ilişkileri ayarlıyoruz
        ShoppingItem toSave = new ShoppingItem();
        toSave.setItem(item.get());
        toSave.setUser(user.get());
        toSave.setShoppingList(shoppingList.get());  // ShoppingList'i ilişkilendiriyoruz
        toSave.setQuantity(shoppingItem.getQuantity());  // Miktarı ayarlıyoruz

        shoppingItemRepository.save(toSave);
    }

    // ID'ye göre ShoppingItem silme
    public void deleteShoppingItem(Long id) {
        shoppingItemRepository.deleteById(id);
    }

    // shoppingItemId'ye bağlı olarak Item'ları döndüren metod
    public List<Item> getItemsInAShoppingItem(long shoppingItemId) {
        Optional<ShoppingItem> shoppingItemOptional = shoppingItemRepository.findById(shoppingItemId);

        if (shoppingItemOptional.isEmpty()) {
            return null; // Eğer ShoppingItem yoksa, null dönebiliriz
        }

        ShoppingItem shoppingItem = shoppingItemOptional.get();

        // ShoppingItem'a bağlı Item'ı döndür
        return List.of(shoppingItem.getItem()); // Bu ilişkide her ShoppingItem sadece bir Item'a sahiptir
    }
}