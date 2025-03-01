package com.example.ShopingList.requests;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateShoppingItemRequest {

    private long itemId;
    private long userId;
    private double quantity;
    private long ShoppingListId ;

}
