package com.example.justeat;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.justeat.Models.OrdersModel;
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
                binding.detailPriceLbl.setText(String.format("%d", price));
                binding.detailFoodItemName.setText(name);
                binding.detailDescription.setText(description);
                binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));

                binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isValidString = checkForValidStringInput();

                        if (isValidString) {
                            boolean isInserted = helper.insertOrder(
                                    binding.detailCustomerNameEdt.getText().toString(),
                                    binding.detailCustomerPhoneEdt.getText().toString(),
                                    price,
                                    image,
                                    description,
                                    name,
                                    foodQty
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

                binding.detailFoodItemName.setText(cursor.getString(7));
                binding.detailDescription.setText(cursor.getString(6));

                foodQty = cursor.getInt(5);
                binding.detailFoodItemQtyTv.setText(String.valueOf(foodQty));
                int itemImageID = cursor.getInt(4);
                binding.detailFoodItemImage.setImageResource(itemImageID);

                binding.detailPriceLbl.setText(String.valueOf(cursor.getInt(3)));
                binding.detailCustomerPhoneEdt.setText(cursor.getString(2));

                binding.detailCustomerNameEdt.setText(cursor.getString(1));

                binding.insertBtn.setText("Update Now");
                binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // checks if the Name and Phone fields are valid inputs
                        boolean isValidString = checkForValidStringInput();

                        if (isValidString) {
                            boolean isUpdated = helper.updateOrder(
                                    binding.detailCustomerNameEdt.getText().toString(),
                                    binding.detailCustomerPhoneEdt.getText().toString(),
                                    Integer.parseInt(binding.detailPriceLbl.getText().toString()),
                                    itemImageID,
                                    binding.detailDescription.getText().toString(),
                                    binding.detailFoodItemName.getText().toString(),
                                    foodQty,
                                    id
                            );

                            if (isUpdated) {
                                Toast.makeText(
                                        DetailActivity.this,
                                        "Updated",
                                        Toast.LENGTH_SHORT
                                ).show();
                                cursor.close();
                            } else {
                                Toast.makeText(
                                        DetailActivity.this,
                                        "Failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB Exception", "DB Exception Occurred: " + e);
        }

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

    }

    private boolean checkForValidStringInput() {

        boolean isValid = false;

        String name = binding.detailCustomerNameEdt.getText().toString().trim();
        String phone = binding.detailCustomerPhoneEdt.getText().toString().trim();

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