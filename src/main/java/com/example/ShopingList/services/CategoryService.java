package com.example.ShopingList.services;

import com.example.ShopingList.entities.Category;
import com.example.ShopingList.repos.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private  CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category addCategory(Category category) {
        // Burada kategori objesi null kontrolü yapılabilir
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        // Kategoriyi veritabanına kaydediyoruz
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("category not found");
        }
        
    }
}
