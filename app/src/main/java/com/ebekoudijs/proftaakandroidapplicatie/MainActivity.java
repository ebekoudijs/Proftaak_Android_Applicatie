package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebekoudijs.proftaakandroidapplicatie.services.IUserService;
import com.ebekoudijs.proftaakandroidapplicatie.services.UserService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String spinnerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IUserService userService = new UserService();

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextAge = findViewById(R.id.editTextAge);
        Button Submit = findViewById(R.id.buttonCreate);
        Button ToLogin = findViewById(R.id.buttonToLogin);
        Spinner spinner = findViewById(R.id.spinnerGender);

        //array for dropdown menu of genders
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //create user button
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(editTextName.getText().toString(),editTextPassword.getText().toString(), editTextEmail.getText().toString(),
                        Integer.parseInt(editTextAge.getText().toString()), spinnerContent);
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

        //button to login page
        ToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
    }

    //dropdown menu
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerContent = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}