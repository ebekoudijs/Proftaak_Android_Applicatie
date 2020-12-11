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

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IUserService userService = new UserService();


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

                TaskRunner.executeAsync(() -> userService.createUser(user), (result)-> {
                    if (result.isPresent()){
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

}