package com.ebekoudijs.proftaakandroidapplicatie.services;

import android.util.Base64;
import android.util.Log;

import com.ebekoudijs.proftaakandroidapplicatie.Product;
import com.ebekoudijs.proftaakandroidapplicatie.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class OrderService extends ApiWrapper implements IOrderService {

    private static final String TAG = "OrderService";

    public List<Product> getDrinks() {
        try{
            HttpURLConnection conn = apiConnect(GET, "getdrinks");

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

            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            List<Product> products = gson.<ArrayList<Product>>fromJson(content.toString(), type);
            Log.d(TAG, products.toString());
            String json = gson.toJson(products);
            Log.d(TAG, json);

            return products;
        }
        catch (Exception e) {
            Log.e(TAG, "getDrinks: ", e);
            return null;
        }
    }

    public Order addOrder(Order order) {
        try {
            HttpURLConnection conn = apiConnect(POST, "addorder");

            Gson gson = new Gson();
            String json = gson.toJson(order);
            Log.i("JSON", json);

            String usernamePassword = "Hetti" + ":" + "Oj1dhwRpk";
            String base64 = new String(Base64.encode(usernamePassword.getBytes(), Base64.DEFAULT));
            conn.addRequestProperty("Authorization", "Basic " + base64);

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
            return gson.fromJson(content.toString(), Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
