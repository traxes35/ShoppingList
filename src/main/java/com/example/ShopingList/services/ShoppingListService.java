package com.example.ShopingList.services;


import com.example.ShopingList.entities.ShoppingList;
import com.example.ShopingList.entities.User;
import com.example.ShopingList.repos.ShoppingListRepository;
import com.example.ShopingList.repos.UserRepository;
import com.example.ShopingList.requests.CreateShoppingListRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {
    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final UserService userService;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, UserRepository userRepository, UserService userService) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    // Tüm ShoppingList'leri döndüren metod
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    // ID'ye göre ShoppingList döndüren metod
    public ShoppingList getShoppingListById(Long id) {
        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        return shoppingList.orElse(null); // Eğer yoksa null döner
    }

    // Kullanıcı ID'sine göre ShoppingList'leri döndüren metod
    public Optional<ShoppingList> getShoppingListsByUserId(Long userId) {
        return shoppingListRepository.findByUserId(userId);
    }

    public void addShoppingList(CreateShoppingListRequest shoppingListRequest) {
        // Kullanıcıyı veritabanından alıyoruz
        Optional<User> user = userService.userRepository.findById(shoppingListRequest.getUserId());

        // Kullanıcı bulunamazsa, hata mesajı döndürüyoruz
        if (!user.isPresent()) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Yeni ShoppingList nesnesi oluşturuyoruz
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setUser(user.get());  // ShoppingList'i kullanıcıyla ilişkilendiriyoruz
        shoppingList.setTitle(shoppingListRequest.getTitle());  // Başlığı ayarlıyoruz
        shoppingList.setCreatedAt(LocalDateTime.now());  // Oluşturulma tarihini ayarlıyoruz

        // ShoppingList'i kaydediyoruz
        shoppingListRepository.save(shoppingList);
    }

    // ShoppingList güncelleme metodu
    public void updateShoppingList(Long id, ShoppingList updatedShoppingList) {
        Optional<ShoppingList> existingShoppingList = shoppingListRepository.findById(id);
        if (existingShoppingList.isPresent()) {
            ShoppingList shoppingList = existingShoppingList.get();
            shoppingList.setTitle(updatedShoppingList.getTitle());
            shoppingList.setCreatedAt(updatedShoppingList.getCreatedAt());
            shoppingList.setUser(updatedShoppingList.getUser());
            // ShoppingItem'lar orada da güncellenebilir
            shoppingListRepository.save(shoppingList);
        }
    }

    // ShoppingList silme metodu
    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }
}