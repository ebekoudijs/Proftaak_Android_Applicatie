package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ebekoudijs.proftaakandroidapplicatie.services.IOrderService;
import com.ebekoudijs.proftaakandroidapplicatie.services.OrderService;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends AppCompatActivity {

    IOrderService orderService = new OrderService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TaskRunner.executeAsync(() -> orderService.getDrinks(), (result)-> {
            if (result.isPresent()) {
                RecyclerView RecyclerDrinks = findViewById(R.id.RecyclerDrinks);

                RecyclerDrinks.setAdapter(new RecyclerViewAdapter(result.get()));

                RecyclerDrinks.setLayoutManager(new LinearLayoutManager(this));

                //Intent i = new Intent(Login.this, Order.class);
                //startActivity(i);
            }
            Toast.makeText(getApplicationContext(), RecyclerViewAdapter.ViewHolder.fullOrder, Toast.LENGTH_LONG);

        });
    }
}