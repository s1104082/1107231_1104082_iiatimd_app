package com.example.animalcrossingfront.database;


import android.content.Context;

public class User  {
    private String username;
    private String email;
    private String password;
    private String token;

    public User(String email, String token) {
        this.email = email;
        this.token = token;
    }

}
