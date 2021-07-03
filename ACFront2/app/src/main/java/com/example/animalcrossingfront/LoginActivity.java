package com.example.animalcrossingfront;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!UserAuth.getInstance(this).isAuth()){

            // USER IS LOGGED IN
        } else {
            Intent toHomeScreen = new Intent(this, TestActivity.class);
            startActivity(toHomeScreen);

        }
        setContentView(R.layout.activity_login);

        Button registerButton = findViewById(R.id.loginButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginScreen();
            }
        });

        final Button backToStart = findViewById(R.id.backToStartButton2);
        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });


    }
    public void backToHome(){
        Intent loginIntent = new Intent(this, StartActivity.class);
        startActivity(loginIntent);

    }

    public void LoginScreen(){
        EditText mail = findViewById(R.id.editLoginEmailAddress);
        final String email = mail.getText().toString();

        EditText pass = findViewById(R.id.editLoginPassword);
        final String password = pass.getText().toString();

        String URL_Login  = "http://10.0.2.2:8000/api/login?email=" + email + "&password=" + password;


        JSONObject user = getLoginData(email, password);
        final Context context = this;

        if(user != null) {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL_Login, user, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    try {
                      token = response.getString("access_token");

                    } catch (JSONException e) {

                    }
                    AuthenticateUser(email, password, token, true);
                    Intent toDashboardScreenIntent = new Intent(context, StartActivity.class);
                    startActivity(toDashboardScreenIntent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", String.valueOf(error));
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(LoginActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(LoginActivity.this, "Couldn't resolve login", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError){
                        Toast.makeText(LoginActivity.this, "No network was found...", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            jor.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
            VolleySingleton.getInstance(this).addToRequestQueue(jor);
        }
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


    private String getToken(){
        return UserAuth.getInstance(this).getToken();
    }


}
