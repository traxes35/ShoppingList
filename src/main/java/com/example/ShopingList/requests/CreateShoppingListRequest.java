package com.example.ShopingList.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShoppingListRequest {
    private Long userId;
    private String title;

}
