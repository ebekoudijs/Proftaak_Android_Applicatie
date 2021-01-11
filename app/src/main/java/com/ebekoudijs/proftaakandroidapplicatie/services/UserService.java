package com.ebekoudijs.proftaakandroidapplicatie.services;

import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.ebekoudijs.proftaakandroidapplicatie.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService extends ApiWrapper implements IUserService {
    private static final String TAG = "UserService";

    @Override
    public User getUser(String email, String password) {
            try {
                String emailPassword = email + ":" + password;
                HttpURLConnection conn = apiConnect(GET, "getuser");
                String base64 = new String(Base64.encode(emailPassword.getBytes(), Base64.DEFAULT));
                conn.addRequestProperty("Authorization", "Basic " + base64);

                Log.i("STATUS" , String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());

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

                Log.d(TAG, "getUser: " + content.toString());
                return gson.fromJson(content.toString(), User.class);
            } catch (Exception e) {
                e.printStackTrace();
                return(null);
            }
    }


    @Override
    public User createUser(User user) {
        try {
            HttpURLConnection conn = apiConnect(POST, "createuser");

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