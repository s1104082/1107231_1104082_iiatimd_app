package com.example.animalcrossingfront;

import android.content.Intent;
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
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

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
//
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//
//        unbindDrawables(findViewById(R.id.registerView));
//        System.gc();
//    }
//
//
//    @Override
//    protected void onDestroy()
//    {
//        super.onDestroy();
//
//        unbindDrawables(findViewById(R.id.registerView));
//        System.gc();
//    }
//
//    private void unbindDrawables(View view)
//    {
//        if (view.getBackground() != null)
//        {
//            view.getBackground().setCallback(null);
//        }
//        if (view instanceof ViewGroup && !(view instanceof AdapterView))
//        {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
//            {
//                unbindDrawables(((ViewGroup) view).getChildAt(i));
//            }
//            ((ViewGroup) view).removeAllViews();
//        }
//    }



    public void backToHome() {
        Intent loginIntent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(loginIntent);
    }


    public void registerUser() {
        final Intent toLoginScreen = new Intent(getApplicationContext(), LoginActivity.class);
        toLoginScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                            String token = null;
                            try {
                                token = response.getString("access_token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            setUserData(username, email, password);
                            Log.d("taken", token);
                             assignToken(token);
                            logIn();

                            Toast.makeText(getApplicationContext(), "Reg Successful", Toast.LENGTH_SHORT).show();
                            startActivity(toLoginScreen);


                        }, error -> {
                            Log.d("error", String.valueOf(error));
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "NoConnection reg", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                Toast.makeText(getApplicationContext(), "Connection time out reg", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(), "Couldn't resolve login", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(), "No network was found...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
                        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);


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








