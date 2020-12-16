package com.ebekoudijs.proftaakandroidapplicatie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Product> products;


    public RecyclerViewAdapter(List<Product> products) {
        this.products = products;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.drinklistitem, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(products.get(position).name);

        viewHolder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.drinkAmount++;
                viewHolder.textViewDrinkAmount.setText(String.valueOf(viewHolder.drinkAmount));
            }
        });

        viewHolder.buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.drinkAmount > 0) {
                    viewHolder.drinkAmount--;
                    viewHolder.textViewDrinkAmount.setText(String.valueOf(viewHolder.drinkAmount));
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button buttonPlus, buttonMin;
        private final TextView textViewDrinkAmount;
        private int drinkAmount = 0;
        public static String fullOrder;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textViewDrink);
            buttonPlus = view.findViewById(R.id.buttonPlus);
            buttonMin = view.findViewById(R.id.buttonMinus);
            textViewDrinkAmount = view.findViewById(R.id.textViewDrinkAmount);
            fullOrder = textViewDrinkAmount.toString() + " " + textView.toString();
        }

        public TextView getTextView() {
            return textView;
        }
    }
}