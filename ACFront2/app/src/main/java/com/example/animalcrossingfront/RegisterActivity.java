package com.example.animalcrossingfront;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
//    @Charlaine For Code INFO
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
        Intent loginIntent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(loginIntent);
    }


    public void registerUser() {
        final Intent toLoginScreen = new Intent(getApplicationContext(), LoginActivity.class);
        toLoginScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                final String username = sName.getText().toString();
                final String email = sEmail.getText().toString();
                final String password = sPassword.getText().toString();
                final String conPassword = sConPassword.getText().toString();

                if (password.equals(conPassword)) {
                    JSONObject newUserData = getRegisterData(username, email, password);

                    if (newUserData != null) {
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_CHAR, newUserData, response -> {
                        Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                        startActivity(toLoginScreen);

                        }, error -> {
                            Log.d("error", String.valueOf(error));
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "NoConnection", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getApplicationContext(), "Connection time out", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(), "No network was found", Toast.LENGTH_SHORT).show();
                            }
                        });
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
                        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);

                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }

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






}








