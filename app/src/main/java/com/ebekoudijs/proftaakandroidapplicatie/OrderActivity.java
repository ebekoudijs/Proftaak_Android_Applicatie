package com.ebekoudijs.proftaakandroidapplicatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ebekoudijs.proftaakandroidapplicatie.services.IOrderService;
import com.ebekoudijs.proftaakandroidapplicatie.services.Order;
import com.ebekoudijs.proftaakandroidapplicatie.services.OrderService;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    IOrderService orderService = new OrderService();
    EditText message;
    EditText tableNumber;
    Button buttonOrder;
    RecyclerView recyclerDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Log.d(TAG, "onClick: " + Login.loggedUser.toString());

        message = findViewById(R.id.editTextMessage);
        tableNumber = findViewById(R.id.editTextTable);
        buttonOrder = findViewById(R.id.buttonOrder);
        recyclerDrinks = findViewById(R.id.RecyclerDrinks);


        //populating recyclerView
        TaskRunner.executeAsync(() -> orderService.getDrinks(), (result) -> {
            if (result.isPresent()) {
                RecyclerView RecyclerDrinks = recyclerDrinks;

                RecyclerDrinks.setAdapter(new RecyclerViewAdapter(result.get()));

                RecyclerDrinks.setLayoutManager(new LinearLayoutManager(this));
            }
        });

        //order button
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order  order = getOrder();
                TaskRunner.executeAsync(() -> orderService.addOrder(order), (result)-> {
                    if (result.isPresent()){
                        Toast.makeText(getApplicationContext(), "Order sent successfully!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Order NOT sent successfully!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

        //getting full order (message, tableNumber, productLines)
        public Order getOrder(){
        ArrayList<ProductLine> productLines = new ArrayList<>();

            RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerDrinks.getAdapter();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                RecyclerViewAdapter.ViewHolder holder = adapter.viewHolders[i];
                ProductLine productLine = new ProductLine(holder.getDrinkId(), holder.getDrinkAmount());
                productLines.add(productLine);
            }

            return new Order(message.getText().toString(), Integer.parseInt(tableNumber.getText().toString()), productLines);
        }
    }
