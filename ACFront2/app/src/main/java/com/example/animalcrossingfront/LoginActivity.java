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
import com.example.animalcrossingfront.database.User;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    // @Charlaine For Code INFO
    EditText sName, sEmail, sPassword, sConPassword;
    private String token = null;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sName = (EditText) findViewById(R.id.editRegisterName);
        sEmail = (EditText) findViewById(R.id.editRegisterEmailAddress);
        sPassword = (EditText) findViewById(R.id.editRegisterPassword);
        sConPassword = (EditText) findViewById(R.id.editRegisterPasswordConfirm);

        if(!UserAuth.getInstance(this).isAuth()){

        } else {
            Intent toHomeScreen = new Intent(this, LoginActivity.class);
            startActivity(toHomeScreen);

        }

        Button registerButton = findViewById(R.id.loginButton);
        registerButton.setOnClickListener(v -> LoginUser());

        final Button backToStart = findViewById(R.id.backToStartButton2);
        backToStart.setOnClickListener(v -> backToHome());

    }

    public void backToHome(){
        Intent loginIntent = new Intent(this, StartActivity.class);
        startActivity(loginIntent);
    }


    public void LoginUser(){
        EditText pass = findViewById(R.id.editLoginPassword);
        EditText mail = findViewById(R.id.editLoginEmailAddress);

        final String email = mail.getText().toString();
        final String password = pass.getText().toString();

        String URL_Login  = "http://10.0.2.2:8000/api/login?email=" + email + "&password=" + password;

        try {
            JSONObject jo = new JSONObject();
            jo.put("email", email);
            jo.put("password", password);

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL_Login, jo, response -> {
                Log.d("response", response.toString());
                try {
                    token = "Bearer " + response.getString("access_token");
                    Log.d("loginauth", "LoginUser: " + token);
                    jo.put("token", token);

                    SharedPreferences preferences=getSharedPreferences("myPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("token",token);
                    editor.apply();


                    user = new User(email, token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent toMainScreenIntent = new Intent(LoginActivity.this, TrackerActivity.class);
                    startActivity(toMainScreenIntent);

            }, error -> {
                Log.d("error", String.valueOf(error));
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError){
                    Toast.makeText(LoginActivity.this, "No network was found", Toast.LENGTH_SHORT).show();
                }
            });
            jor.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
            VolleySingleton.getInstance(this).addToRequestQueue(jor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
