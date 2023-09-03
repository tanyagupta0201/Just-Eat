package com.example.justeat;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.util.Log;
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
        try (DBHelper helper = new DBHelper(this)) {

            if (getIntent().getIntExtra("type", 0) == 1) {

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

                        boolean isValidString = checkForValidStringInput();

                        if (isValidString) {
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
                    }
                });

            } else {
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

                        // checks if the Name and Phone fields are valid inputs
                        boolean isValidString = checkForValidStringInput();

                        Toast.makeText(DetailActivity.this, "isValidString: " + isValidString, Toast.LENGTH_SHORT).show();

                        if (isValidString) {
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

                            if (isUpdated)
                                Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB Exception", "DB Exception Occurred: " + e);
        }

    }

    private boolean checkForValidStringInput() {

        boolean isValid = false;

        String name = binding.nameBox.getText().toString().trim();
        String phone = binding.phoneBox.getText().toString().trim();

        if (!name.isEmpty()) {
            if (phone.length() == 10) {
                isValid = true;
            } else {
                Toast.makeText(this, "Please enter a valid Phone Number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void addFoodQuantity() {
        foodQty += 1;
        binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));
    }

    private void subtractFoodQuantity() {
        if (foodQty > 1) {
            foodQty -= 1;
            binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));
        } else {
            Toast.makeText(this, "Can't reduce quantity any further", Toast.LENGTH_SHORT).show();
        }
    }

}