package com.example.ShopingList.requests;

import lombok.Getter;
import lombok.Setter;
import com.example.ShopingList.entities.Category;
import com.example.ShopingList.entities.QuantityUnit;


@Getter
@Setter
public class CreateItemRequest {

    private String name;
    private Double price;
    private Long categoryId; // category_id yerine sadece category_id'nin id'si
    private Long quantityUnitId;
}