package com.example.animalcrossingfront;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.animalcrossingfront.database.SharedPrefManager;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText sName, sEmail, sPassword, sConPassword;
    private String token = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sName = (EditText) findViewById(R.id.editRegisterName);
        sEmail = (EditText) findViewById(R.id.editRegisterEmailAddress);
        sPassword = (EditText) findViewById(R.id.editRegisterPassword);
        sConPassword = (EditText) findViewById(R.id.editRegisterPasswordConfirm);


        if(!UserAuth.getInstance(this).isAuth()){

        } else {
            Intent toHomeScreen = new Intent(this, LoginActivity.class);
            startActivity(toHomeScreen);

        }
        setContentView(R.layout.activity_login);

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
        EditText mail = findViewById(R.id.editLoginEmailAddress);
        final String email = mail.getText().toString();

        EditText pass = findViewById(R.id.editLoginPassword);
        final String password = pass.getText().toString();

        String URL_Login  = "http://10.0.2.2:8000/api/login?email=" + email + "&password=" + password;

        JSONObject user = getLoginData(email, password);
        final Context context = this;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL_Login, user, response -> {
            Log.d("response", response.toString());
            try {
              token = response.getString("access_token");
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("userToken", token);
                editor.apply();


            } catch (JSONException e) {
                    e.printStackTrace();
            }
            AuthenticateUser(email, password, token, true);
            Intent toMainScreenIntent = new Intent(context, TrackerActivity.class);
            startActivity(toMainScreenIntent);

        }, error -> {
            Log.d("error", String.valueOf(error));
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(LoginActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(LoginActivity.this, "Couldn't resolve login", Toast.LENGTH_SHORT).show();
            } else if (error instanceof NetworkError){
                Toast.makeText(LoginActivity.this, "No network was found...", Toast.LENGTH_SHORT).show();
            }

        });
        jor.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
        VolleySingleton.getInstance(this).addToRequestQueue(jor);
    }



    private JSONObject getLoginData(String email, String password){
        JSONObject user = new JSONObject();
        try{
            user.put("email", email);
            user.put("password", password);
        } catch(Exception e){}
            return user;
    }

    private void AuthenticateUser(String email, final String password, String jwt, boolean isAuth) {
        UserAuth.getInstance(this).setEmail(email);
        UserAuth.getInstance(this).setPassword(password);
        UserAuth.getInstance(this).setToken(jwt);
        UserAuth.getInstance(this).setAuth(isAuth);
    }





}
