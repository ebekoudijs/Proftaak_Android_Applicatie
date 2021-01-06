package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebekoudijs.proftaakandroidapplicatie.services.IUserService;
import com.ebekoudijs.proftaakandroidapplicatie.services.UserService;

public class Login extends AppCompatActivity {

    public static User loggedUser;
    IUserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button toCreateAccount = findViewById(R.id.buttonToCreateAccount);
        Button login = findViewById(R.id.buttonLogin);
        Button toOrder = findViewById(R.id.buttonToOrder);
        EditText email = findViewById(R.id.editTextLoginEmail);
        EditText password = findViewById(R.id.editTextLoginPassword);


        //temporary button to order
        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, OrderActivity.class);
                startActivity(i);
            }
        });


        //back to account creation button
        toCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(email.getText().toString(), password.getText().toString());

                TaskRunner.executeAsync(() -> userService.getUser(user.Username, user.Password), (result)-> {
                    if (result.isPresent()){
                        loggedUser = result.get();
                        Toast.makeText(getApplicationContext(), "Credentials sent successfully!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, OrderActivity.class);
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