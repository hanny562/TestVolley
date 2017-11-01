package com.example.hanny.testvolley;


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

public class RequestQueue extends Application{
    public static final String TAG = RequestQueue.class.getSimpleName();

    private com.android.volley.RequestQueue mRequestQueue;

    private static RequestQueue mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized RequestQueue getInstance() {
        return mInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
