package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    public String ipAddress(String address) {
        return "145.93.128.139:5000/aapie/" + address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button ToCreateAccount = findViewById(R.id.buttonToCreateAccount);
        Button Login = findViewById(R.id.buttonLogin);
        EditText EditTextLoginUser = findViewById(R.id.editTextLoginUsername);
        EditText EditTextLoginPass = findViewById(R.id.editTextLoginPassword);

        ToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(EditTextLoginUser.getText().toString(), EditTextLoginPass.getText().toString(), null);

                TaskRunner.executeAsync(() -> loginUser(user), (result)-> {
                    if (result.isPresent() && result.get()){
                        Toast.makeText(getApplicationContext(), "Credentials sent successfully.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Credentials NOT sent successfully!", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }

    private boolean loginUser(User user){
        try {
            URL url = new URL(ipAddress("login"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);

            Gson gson = new Gson();
            String json = gson.toJson(user);

            Log.i("JSON", json);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(json);

            os.flush();
            os.close();
          


            Log.i("STATUS" , String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            String readLine = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            while((readLine = in .readLine()) !=null){
                response.append(readLine);
            } in .close();
            Log.d(TAG, "loginUser: " + response.toString());

            if (response.toString().isEmpty()) {
                Intent i = new Intent(Login.this, Order.class);
                startActivity(i);
            }

            else {
                Toast.makeText(getApplicationContext(), "Inloggen mislukt :(", Toast.LENGTH_LONG).show();
            }

            conn.disconnect();
            return(true);

        } catch (Exception e) {
            Log.e(TAG, "loginUser: ", e);
            e.printStackTrace();
            return(false);
        }
    }
}