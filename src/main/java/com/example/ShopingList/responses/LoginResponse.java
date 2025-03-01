package com.example.ShopingList.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private String username;

    public LoginResponse(String token, long expiresIn, String username) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
    }
}