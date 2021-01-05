package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ebekoudijs.proftaakandroidapplicatie.services.IOrderService;
import com.ebekoudijs.proftaakandroidapplicatie.services.Order;
import com.ebekoudijs.proftaakandroidapplicatie.services.OrderService;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    IOrderService orderService = new OrderService();
    private static final String TAG = "Order";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TaskRunner.executeAsync(() -> orderService.getDrinks(), (result) -> {
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
                Order  order = getOrder();
                TaskRunner.executeAsync(() -> orderService.addOrder(order), (result)-> {
                    if (result.isPresent()){
                        Toast.makeText(getApplicationContext(), "Order sent successfully.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Order NOT sent successfully!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

        public Order getOrder(){
        ArrayList<ProductLine> productLines = new ArrayList<ProductLine>();

            RecyclerView RecyclerDrinks = findViewById(R.id.RecyclerDrinks);

            RecyclerViewAdapter adapter = (RecyclerViewAdapter) RecyclerDrinks.getAdapter();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                RecyclerViewAdapter.ViewHolder holder = adapter.viewHolders[i];
                ProductLine productLine = new ProductLine(holder.getDrinkId(), holder.getDrinkAmount());
                productLines.add(productLine);
            }

            Order order = new Order("Groetjes van Piet Paulusma!1!", null, 3, productLines);
            return order;
        }
    }
