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


        EditText EditTextusername = findViewById(R.id.editTextUsername);
        EditText EditTextpassword = findViewById(R.id.editTextPassword);
        EditText EditTextphoneNumber = findViewById(R.id.editTextPhoneNumber);
        Button submit = findViewById(R.id.buttonCreate);
        Button toLogin = findViewById(R.id.buttonToLogin);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(EditTextusername.getText().toString(), EditTextpassword.getText().toString(), EditTextphoneNumber.getText().toString());

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

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
    }

}