package com.example.justeat;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.justeat.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    int foodQty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // using database now
        DBHelper helper = new DBHelper(this);

        if(getIntent().getIntExtra("type", 0) == 1) {

        // Abb yahan intent receive karna hai
        final int image = getIntent().getIntExtra("image", 0);
        final int price = Integer.parseInt(getIntent().getStringExtra("price"));
        final String name = getIntent().getStringExtra("name");
        final String description = getIntent().getStringExtra("desc");

        binding.detailFoodItemImage.setImageResource(image);
        binding.priceLbl.setText(String.format("%d", price));
        binding.detailFoodItemName.setText(name);
        binding.detailDescription.setText(description);
        binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));

        binding.detailFoodItemQtyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodQuantity();
            }
        });

        binding.detailFoodItemQtySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractFoodQuantity();
            }
        });

        binding.insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = helper.insertOrder(
                        binding.nameBox.getText().toString(),
                        binding.phoneBox.getText().toString(),
                        price,
                        image,
                        name,
                        description,
                        Integer.parseInt(binding.detailFoodItemQtyTv.getText().toString())
                );

                if (isInserted)
                    Toast.makeText(DetailActivity.this, "Data Success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
      }
      else
      {
          // Update Orders

          int id = getIntent().getIntExtra("id", 0);
          Cursor cursor = helper.getOrderById(id);
          int image = cursor.getInt(4);

          binding.detailFoodItemImage.setImageResource(image);
          binding.priceLbl.setText(String.format("%d", cursor.getInt(3)));
          binding.nameBox.setText(cursor.getString(6));
          binding.detailDescription.setText(cursor.getString(5));

          binding.nameBox.setText(cursor.getString(1));
          binding.phoneBox.setText(cursor.getString(2));

          binding.insertBtn.setText("Update Now");
          binding.insertBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                boolean isUpdated = helper.updateOrder(
                         binding.nameBox.getText().toString(),
                         binding.phoneBox.getText().toString(),
                         Integer.parseInt(binding.priceLbl.getText().toString()),
                         image,
                         binding.detailDescription.getText().toString(),
                         binding.nameBox.getText().toString(),
                         1,
                         id
                 );

                 if(isUpdated)
                     Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                 else
                     Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();

              }
          });
      }
    }

    private void addFoodQuantity() {
        foodQty += 1;
        binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));
    }

    private void subtractFoodQuantity() {
        if(foodQty > 1) {
            foodQty -= 1;
            binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));
        } else {
            Toast.makeText(this, "Can't reduce quantity any further", Toast.LENGTH_SHORT).show();
        }
    }

}