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

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText EditTextUsername = findViewById(R.id.editTextUsername);
        EditText EditTextPassword = findViewById(R.id.editTextPassword);
        EditText EditTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Button Submit = findViewById(R.id.buttonCreate);
        Button ToLogin = findViewById(R.id.buttonToLogin);
        Button ToOrder = findViewById(R.id.buttonToOrder);

        ToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Order.class);
                startActivity(i);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(EditTextUsername.getText().toString(), EditTextPassword.getText().toString(), EditTextPhoneNumber.getText().toString());

                TaskRunner.executeAsync(() -> createUser(user), (result)-> {
                    if (result.isPresent() && result.get()){
                        Toast.makeText(getApplicationContext(), "Credentials sent successfully.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Credentials NOT sent successfully!", Toast.LENGTH_LONG).show();
                    }

                });

            }
        });

        ToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
    }

    private boolean createUser(User user){
        try {
            URL url = new URL("http://212.127.230.61:5000/aapie/post");
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



            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
            return(true);
        } catch (Exception e) {
            e.printStackTrace();
            return(false);
        }
    }
}