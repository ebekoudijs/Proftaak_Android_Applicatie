package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ebekoudijs.proftaakandroidapplicatie.services.IOrderService;
import com.ebekoudijs.proftaakandroidapplicatie.services.OrderService;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends AppCompatActivity {

    IOrderService orderService = new OrderService();
    private static final String TAG = "Order";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TaskRunner.executeAsync(() -> orderService.getDrinks(), (result)-> {
            if (result.isPresent()) {
                RecyclerView RecyclerDrinks = findViewById(R.id.RecyclerDrinks);

                RecyclerDrinks.setAdapter(new RecyclerViewAdapter(result.get()));

                RecyclerDrinks.setLayoutManager(new LinearLayoutManager(this));
            }
        });
        Button buttonOrder = findViewById(R.id.buttonOrder);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView RecyclerDrinks = findViewById(R.id.RecyclerDrinks);

                RecyclerViewAdapter adapter = (RecyclerViewAdapter) RecyclerDrinks.getAdapter();
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    RecyclerViewAdapter.ViewHolder holder = adapter.viewHolders[i];
                    Log.d(TAG, "onClick: " + holder.getFullOrder());
                }
            }
        });
    }
}