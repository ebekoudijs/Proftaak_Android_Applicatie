package com.ebekoudijs.proftaakandroidapplicatie.services;

import android.util.Base64;
import android.util.Log;

import com.ebekoudijs.proftaakandroidapplicatie.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService implements IUserService {

    @Override
    public User getUser(String userName, String password) {
            try {
                String usernamePassword = userName + ":" + password;
                HttpURLConnection conn = apiConnect("GET", "getuser");
                String base64 = new String(Base64.encode(usernamePassword.getBytes(), Base64.DEFAULT));
                conn.addRequestProperty("Authorization", "Basic " + base64);

                Log.i("STATUS" , String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                //Log.d(TAG, "loginUser: " + response.toString());
                Gson gson = new Gson();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                return gson.fromJson(content.toString(), User.class);
            } catch (Exception e) {
                //Log.e(TAG, "loginUser: ", e);
                e.printStackTrace();
                return(null);
            }
        }

    public HttpURLConnection apiConnect(String requestMethod, String uri) {
        try {
            URL url = new URL ("http://145.93.128.113:5000/aapie/" + uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public User createUser(User user) {
        try {
            HttpURLConnection conn = apiConnect("POST", "createuser");

            Gson gson = new Gson();
            String json = gson.toJson(user);
            Log.i("JSON", json);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(json);
            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return gson.fromJson(content.toString(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
