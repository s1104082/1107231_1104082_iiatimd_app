package com.example.animalcrossingfront;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.animalcrossingfront.database.SharedPrefManager;
import com.example.animalcrossingfront.database.User;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText sName, sEmail, sPassword, sConPassword;
    String URL_CHAR = "http://10.0.2.2:8000/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sName = (EditText) findViewById(R.id.editRegisterName);
        sEmail = (EditText) findViewById(R.id.editRegisterEmailAddress);
        sPassword = (EditText) findViewById(R.id.editRegisterPassword);
        sConPassword = (EditText) findViewById(R.id.editRegisterPasswordConfirm);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            registerUser();

        });

        final Button backToStart = findViewById(R.id.backToStartButton);
        backToStart.setOnClickListener(v -> backToHome());

    }

    public void backToHome() {
        Intent loginIntent = new Intent(this, StartActivity.class);
        startActivity(loginIntent);
    }


    public void registerUser() {
        final Intent toLoginScreen = new Intent(this, StartActivity.class);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String username = sName.getText().toString();
                final String email = sEmail.getText().toString();
                final String password = sPassword.getText().toString();
                final String conPassword = sConPassword.getText().toString();

                Log.d("username: ", username);
                Log.d("email: ", email);
                Log.d("password", password);
                Log.d("conpassword", conPassword);


                if (password.equals(conPassword)) {
                    JSONObject newUserData = getRegisterData(username, email, password);


                    if (newUserData != null) {

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_CHAR, newUserData, response -> {

                            //                        String token = response.getString("access_token");
                            setUserData(username, email, password);
//                        Log.d("taken", token);
//                        assignToken(token);
                            logIn();

                            Toast.makeText(getApplicationContext(), "Reg Successful", Toast.LENGTH_SHORT).show();
                            startActivity(toLoginScreen);


                        }, error -> {
                            Log.d("error", String.valueOf(error));
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(RegisterActivity.this, "NoConnection reg", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(RegisterActivity.this, "Connection time out reg", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(RegisterActivity.this, "Couldn't resolve login", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(RegisterActivity.this, "No network was found...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


                    }
                }
            }
        });



    }

    private JSONObject getRegisterData(String username, String email, String password) {
        JSONObject user = new JSONObject();
        try {
            user.put("name", username);
            user.put("email", email);
            user.put("password", password);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    private void setUserData(String username, String email, String password) {
        UserAuth.getInstance(this).setUsername(username);
        UserAuth.getInstance(this).setEmail(email);
        UserAuth.getInstance(this).setPassword(password);
    }


    private void assignToken(String token) {
        UserAuth.getInstance(this).setToken(token);
    }

    private void logIn() {
        UserAuth.getInstance(this).setAuth(true);
    }




}








