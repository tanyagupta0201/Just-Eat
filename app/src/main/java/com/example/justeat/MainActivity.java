package com.example.justeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.justeat.Adapters.MainAdapter;
import com.example.justeat.Models.MainModel;
import com.example.justeat.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<MainModel> list = new ArrayList<>();

        list.add(new MainModel(R.drawable.noodles, "Veg Hakka Noodles", "3", "Hakka noodles, soy sauce, hot sauce, chilli oil, sesame oil"));
        list.add(new MainModel(R.drawable.pizza, "Veg Extravaganza", "5", "Black olives, capsicum, onion, grilled mushroom, corn, tomato, jalapeno & extra cheese"));
        list.add(new MainModel(R.drawable.sandwich, "Veg Sandwich", "2", "Savory sandwich with pizza sauce and veggies."));
        list.add(new MainModel(R.drawable.paneer, "Chilli Paneer", "3", "Paneer, soy sauce, spring onion, corn flour, green bell pepper"));
        list.add(new MainModel(R.drawable.momo, "Veg Momos", "4", "Vegetable Momos with Spicy Sesame Tomato Chutney"));
        list.add(new MainModel(R.drawable.burg, "Crispy Veg Burger", "4", "Crispy bhi, Juicy bhi! Our best-selling burger with Veg Potato Patty, fresh onion, lettuce and our signature Veg Mayonnaise."));

        MainAdapter adapter = new MainAdapter(list, this);
        binding.recyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.orders) {
            startActivity(new Intent(MainActivity.this, OrderActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}