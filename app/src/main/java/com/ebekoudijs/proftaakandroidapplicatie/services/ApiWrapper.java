package com.ebekoudijs.proftaakandroidapplicatie.services;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ApiWrapper {

    private static final String TAG = "ApiWrapper";

    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String POST = "POST";

    public HttpURLConnection apiConnect(String requestMethod, String uri) {
        Log.i(TAG, "apiConnect: method: " + requestMethod);
        try {
            URL url = new URL("http://217.123.153.155:5000/aapie/" + uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            if (requestMethod.equals(POST)) {
            conn.setDoOutput(true);
            }
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
