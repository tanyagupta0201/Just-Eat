package com.example.justeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.justeat.Models.OrdersModel;

import java.util.ArrayList;
import java.util.Currency;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBNAME = "mydatabase.db";
    final static int DBVERSION = 1;
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "create table orders" +
                        "(id integer primary key autoincrement," +
                        "name text," +
                        "phone text," +
                        "price int," +
                        "image int," +
                        "quantity int," +
                        "description text," +
                        "foodname text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // jab new food name add hoga tab previous food table delete ho jayega
        // new food ka table create ho jayega aur wo show hone lagega
        sqLiteDatabase.execSQL("DROP table if exists orders");

        // command for creating table again
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(
            String name,
            String phone,
            int price,
            int image,
            String desc,
            String foodName,
            int quantity
    )
    {
        // jo bhi database create hua hoga usko fetch karlega yeh
        SQLiteDatabase database = getReadableDatabase();

        /*
            id = 0
            name = 1
            phone = 2
            price = 3
            image = 4
            desc = 5
            foodName = 6
            quantity = 7
         */

        // key value pair
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("description", desc);
        values.put("foodName", foodName);
        values.put("quantity", quantity);

        long id = database.insert("orders", null, values);

        if (id <= 0)
        {
            // database mein kuch add nhi hua hai
            return false;
        }
        else
        {
            // data added successfully
            return true;
        }

    }

    public ArrayList<OrdersModel> getOrders()
    {
        ArrayList<OrdersModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        /*
            id = 0
            name = 1
            phone = 2
            price = 3
            image = 4
            quantity = 5
            desc = 6
            foodName = 7
         */

        // cursor is used to point the rows
        // works like an iteration
        Cursor cursor = database.rawQuery(
                "Select * from orders",
                null
        );

        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                OrdersModel model = new OrdersModel();

                model.setOrderNumber(cursor.getInt(0) + "");
                model.setOrderTo(cursor.getString(1));
                model.setPhone(cursor.getString(2));
                model.setPrice(cursor.getInt(3) + "");
                model.setOrderImage(cursor.getInt(4));
                model.setQuantity(cursor.getInt(5));
                model.setDescription(cursor.getString(6));
                model.setFoodName(cursor.getString(7));

                orders.add(model);
            }
        }

        cursor.close();
        database.close(); // avoids memory leakage and makes the application efficient

        return orders;
    }

    public Cursor getOrderById(int id) {
        ArrayList<OrdersModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        // cursor is used to point the rows
        // works like an iteration
        Cursor cursor = database.rawQuery(
                "Select * from orders where id = " +id,
                null
        );

        if(cursor != null)
        {
            cursor.moveToFirst();
            Log.i("Orders Model DB", "Orders Model DB Item 1: " + cursor.getInt(0));
        }

        database.close(); // avoids memory leakage and makes the application efficient

        // since we're passing cursor here, so we won't close the cursor here
        // otherwise the cursor will return null
        return cursor;
    }

    public boolean updateOrder(
            String name,
            String phone,
            int price,
            int image,
            String desc,
            String foodName,
            int quantity,
            int id
    ) {
        // jo bhi database create hua hoga usko fetch karlega yeh
        SQLiteDatabase database = getReadableDatabase();

        /*
            id = 0
            name = 1
            phone = 2
            price = 3
            image = 4
            desc = 5
            foodname = 6
            quantity = 7
         */

        // key value pair
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("image", image);
        values.put("description", desc);
        values.put("foodName", foodName);
        values.put("quantity", quantity);

        long row = database.update(
                "orders",
                values,
                "id = "+id,
                null
        );

        if (row <= 0)
        {
            // database mein kuch add nhi hua hai
            return false;
        }
        else
        {
            // data added successfully
            return true;
        }

    }


    // Delete an order
    // if we click on order, jahan uski id match kar jayegi database ki id se, it would be deleted
    public int deletedOrder(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("orders", "id ="+id, null);
    }

}