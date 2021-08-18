package com.example.animalcrossingfront.database;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class VolleySingleton extends Application {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;

    private VolleySingleton(Context ctx){
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context ctx){
        if (instance == null){
            instance = new VolleySingleton(ctx);
        }
        return instance;
    }

    public static VolleySingleton getInstance() {
        return instance;
    }



    public RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

    


}
