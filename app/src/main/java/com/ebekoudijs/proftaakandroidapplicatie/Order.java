package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        RecyclerView RecyclerDrinks = findViewById(R.id.RecyclerDrinks);

        RecyclerDrinks.setAdapter(new RecyclerViewAdapter());
        RecyclerDrinks.setLayoutManager(new LinearLayoutManager(this));

    }

}