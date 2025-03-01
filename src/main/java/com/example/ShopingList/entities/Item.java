package com.example.ShopingList.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "quantity_unit_id")
    private QuantityUnit quantityUnit;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ShoppingItem> shoppingItems; // ShoppingItem ile ilişki

    // Getters and Setters
    private LocalDateTime reminderDate;
}