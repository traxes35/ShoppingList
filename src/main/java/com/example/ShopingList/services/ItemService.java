package com.example.ShopingList.services;

import com.example.ShopingList.entities.Item;
import com.example.ShopingList.entities.Category;
import com.example.ShopingList.entities.QuantityUnit;
import com.example.ShopingList.entities.ShoppingItem;
import com.example.ShopingList.repos.ItemRepository;
import com.example.ShopingList.repos.CategoryRepository;
import com.example.ShopingList.repos.QuantityUnitRepository;
import com.example.ShopingList.repos.ShoppingItemRepository;
import com.example.ShopingList.requests.CreateItemRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final QuantityUnitRepository quantityUnitRepository;
    private final ShoppingItemRepository shoppingItemRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, QuantityUnitRepository quantityUnitRepository, ShoppingItemRepository shoppingItemRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.quantityUnitRepository = quantityUnitRepository;
        this.shoppingItemRepository = shoppingItemRepository;
    }

    // Tüm öğeleri almak
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ID ile öğe almak
    public Item getOneItemsById(long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null); // Eğer item yoksa null döner
    }

    // Öğe ekleme
    public void addItem(CreateItemRequest itemRequest) {
        // Kategoriyi ID'ye göre bulma
        Optional<Category> category = categoryRepository.findById(itemRequest.getCategoryId());
        // Miktar birimini ID'ye göre bulma
        Optional<QuantityUnit> quantityUnit = quantityUnitRepository.findById(itemRequest.getQuantityUnitId());

        if (category.isPresent() && quantityUnit.isPresent()) {
            // Yeni Item oluşturuluyor
            Item item = new Item();
            item.setName(itemRequest.getName());
            item.setPrice(itemRequest.getPrice());
            item.setCategory(category.get());
            item.setQuantityUnit(quantityUnit.get());

            // Veritabanına ekleme
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Geçersiz kategori ya da miktar birimi.");
        }
    }

    // Öğeyi güncelleme
    public void updateItem(int id, CreateItemRequest itemRequest) {
        Optional<Item> itemOptional = itemRepository.findById((long) id);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setName(itemRequest.getName());
            item.setPrice(itemRequest.getPrice());

            Optional<Category> category = categoryRepository.findById(itemRequest.getCategoryId());
            Optional<QuantityUnit> quantityUnit = quantityUnitRepository.findById(itemRequest.getQuantityUnitId());

            if (category.isPresent() && quantityUnit.isPresent()) {
                item.setCategory(category.get());
                item.setQuantityUnit(quantityUnit.get());
            } else {
                throw new IllegalArgumentException("Geçersiz kategori ya da miktar birimi.");
            }

            // Veritabanında güncelleme
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Güncellenecek öğe bulunamadı.");
        }
    }

    // Öğeyi silme
    public void deleteItem(int id) {
        Optional<Item> item = itemRepository.findById((long) id);
        if (item.isPresent()) {
            itemRepository.delete(item.get());
        } else {
            throw new IllegalArgumentException("Silinecek öğe bulunamadı.");
        }
    }

    public List<Item> getItemsInAShoppingItem(long shoppingItemId) {
        // ShoppingItem'ın varlığını kontrol et
        Optional<ShoppingItem> shoppingItemOptional = shoppingItemRepository.findById(shoppingItemId);

        if (shoppingItemOptional.isEmpty()) {
            return null; // Eğer ShoppingItem yoksa, null dönebiliriz
        }

        ShoppingItem shoppingItem = shoppingItemOptional.get();

        // ShoppingItem'a bağlı Item'ları döndür
        return List.of(shoppingItem.getItem()); // Bu ilişkide her ShoppingItem sadece bir Item'a sahiptir, dolayısıyla List'e tek bir item ekliyoruz
    }
}