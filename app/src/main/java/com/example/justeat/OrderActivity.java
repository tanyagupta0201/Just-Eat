package com.example.justeat;

import static com.example.justeat.R.color.lightYellow;
import static com.example.justeat.R.color.yellow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.justeat.Adapters.OrdersAdapter;
import com.example.justeat.Models.OrdersModel;
import com.example.justeat.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(yellow)));

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ArrayList<OrdersModel> list = new ArrayList<>();

        DBHelper helper = new DBHelper(this);
        ArrayList<OrdersModel> list = helper.getOrders();

       /* list.add(new OrdersModel(R.drawable.burg, "Crispy Veg Burger", "4", "12111678"));
        list.add(new OrdersModel(R.drawable.sandwich, "Veg Sandwich", "2", "12111665"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678"));
        list.add(new OrdersModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "12111678")); */

        OrdersAdapter adapter = new OrdersAdapter(list, this);
        binding.ordersRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ordersRecyclerView.setLayoutManager(layoutManager);
    }
}