package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebekoudijs.proftaakandroidapplicatie.services.IUserService;
import com.ebekoudijs.proftaakandroidapplicatie.services.UserService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    public String ipAddress(String address) {
        return "145.93.128.113:5000/aapie/" + address;
    }
    IUserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button ToCreateAccount = findViewById(R.id.buttonToCreateAccount);
        Button Login = findViewById(R.id.buttonLogin);
        Button ToOrder = findViewById(R.id.buttonToOrder);
        EditText EditTextLoginUser = findViewById(R.id.editTextLoginUsername);
        EditText EditTextLoginPass = findViewById(R.id.editTextLoginPassword);


        ToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Order.class);
                startActivity(i);
            }
        });

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

                TaskRunner.executeAsync(() -> userService.getUser(user.Username, user.Password), (result)-> {
                    if (result.isPresent()){
                        Toast.makeText(getApplicationContext(), "Credentials sent successfully!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, Order.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login NOT successful!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}