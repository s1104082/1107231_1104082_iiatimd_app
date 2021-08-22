package com.example.animalcrossingfront.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

public class UserAuth {
    private static UserAuth instance;
    private final Context context;

    private String username;
    private String email;
    private String password;
    private String token;
    private boolean isAuth;

    private SharedPreferences mPrefs;
    private User user;
    private final String userData = "userData";

    private UserAuth(Context context) {
        this.context = context;
    }

    //singleton
    public static synchronized UserAuth getInstance(Context context){
        if (instance == null){
            instance = new UserAuth(context);
        }
        return instance;
    }



    public void setUsername(String username) {
        Log.d("setUsername: ", String.valueOf(username));
        this.username = username;
    }

    public void setEmail(String email) {
        Log.d("setEmail: ", String.valueOf(email));
        this.email = email;
    }

    public void setPassword(String password) {
        Log.d("setPassword: ", String.valueOf(password));
        this.password = password;
    }

    public void setToken(String token) {
        Log.d("setToken: ", String.valueOf(token));
        this.token = token;
    }

    public void setAuth(boolean authenticated) {
        Log.d("setAuthenticated: ", String.valueOf(authenticated));
        isAuth = authenticated;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public SharedPreferences getmPrefs() {
        return mPrefs;
    }

    public User getUser() {
        return user;
    }

    public void setmPrefs(SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
