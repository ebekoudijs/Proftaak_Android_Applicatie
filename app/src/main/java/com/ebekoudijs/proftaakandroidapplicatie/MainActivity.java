package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText EditTextusername = findViewById(R.id.editTextUsername);
        EditText EditTextpassword = findViewById(R.id.editTextPassword);
        EditText EditTextphoneNumber = findViewById(R.id.editTextPhoneNumber);
        Button submit = findViewById(R.id.buttonCreate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(EditTextusername.getText().toString(), EditTextpassword.getText().toString(), EditTextphoneNumber.getText().toString());


                TaskRunner.executeAsync(() -> createUser(user), (result)-> {
                    if (result.isPresent() && result.get() == true){
                        Toast Piemel = Toast.makeText(getApplicationContext(), "grote piemel", Toast.LENGTH_LONG);
                        Piemel.show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "kleine piemel", Toast.LENGTH_LONG).show();
                    }


                });

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