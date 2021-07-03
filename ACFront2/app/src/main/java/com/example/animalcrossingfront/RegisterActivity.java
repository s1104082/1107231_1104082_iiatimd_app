package com.example.animalcrossingfront;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.animalcrossingfront.database.User;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerScreen();
                Log.d("click", "onClick:  ");
            }
        });

        final Button backToStart = findViewById(R.id.backToStartButton);
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

    public void registerScreen(){


        final EditText name, emaila, passworda, conPassworda;

        name = (EditText) findViewById(R.id.editRegisterName);
        emaila = (EditText) findViewById(R.id.editRegisterEmailAddress);
        passworda = (EditText) findViewById(R.id.editRegisterPassword);
        conPassworda = (EditText) findViewById(R.id.editRegisterPasswordConfirm);

        final String username = name.getText().toString();
        final String email = emaila.getText().toString();
        final String password = passworda.getText().toString();
        final String conPassword = conPassworda.getText().toString();

        Log.d("username: " , username);
        Log.d("email: " , email);
        Log.d("password" , password);
        Log.d("conpassword" , conPassword);

        final Intent toLoginScreen = new Intent(this, LoginActivity.class);


//        if (password.equals(conPasswoord)){
            JSONObject newUserData = getRegisterData(username, email, password);
            Log.d("data json: ", String.valueOf(newUserData));
            if(newUserData != null){
                RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
//                final String URL_CHAR = "http://10.0.2.2:8000/api/register?name/" + username + "&email=" + email + "&password=" + password + "&password_confirmation=" + conPassword;
                final String URL_CHAR = "http://10.0.2.2:8000/api/register";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_CHAR, newUserData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        Log.d("url", URL_CHAR);
                        try {
                            String token = response.getString("access_token");

                            setUserData(username, email, password);
                            Log.d("taken", token);
                            assignToken(token);
                            logIn();
                            storeUserInPrefs(new User(username, email, password));
                            //LOOK AT THAT
                            toLoginScreen.putExtra("user", username);
//                            ^^^^^
                        } catch (JSONException e) {
                            Log.d("onResponse: ", String.valueOf(e));
                        }
                        startActivity(toLoginScreen);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(RegisterActivity.this, "NoConnection timeout reg", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError ) {
                            Toast.makeText(RegisterActivity.this, "Connection time out reg", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError) {
                            Toast.makeText(RegisterActivity.this, "Couldn't resolve login", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError){
                            Toast.makeText(RegisterActivity.this, "No network was found...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
                queue.add(jsonObjectRequest);
            }
        }
//    }

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

    private void setUserData(String username, String email, String password){
        UserAuth.getInstance(this).setUsername(username);
        UserAuth.getInstance(this).setEmail(email);
        UserAuth.getInstance(this).setPassword(password);
    }

    private void storeUserInPrefs(User userObj){
        UserAuth.getInstance(this).storeInSharedPrefs(userObj);
    }

    private User retrieveFromPrefs(String key){
        return UserAuth.getInstance(this).retrieveFromSharedPrefs(key);
    }

    private void assignToken(String token){
        UserAuth.getInstance(this).setToken(token);
    }

    private void logIn(){
        UserAuth.getInstance(this).setAuth(true);
    }


}



