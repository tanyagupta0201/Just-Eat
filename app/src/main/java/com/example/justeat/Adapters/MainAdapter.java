package com.example.justeat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justeat.DetailActivity;
import com.example.justeat.Models.MainModel;
import com.example.justeat.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Viewholder> {

    ArrayList<MainModel> list;
    Context context;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_mainfood, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        final MainModel model = list.get(position);
        holder.foodimage.setImageResource(model.getImage());
        holder.mainName.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());

        holder.mainCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kahan se kahan tak jana hai
                Toast.makeText(context, "item clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);

               // Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);

                // saath mein kya lekar jana hai
                intent.putExtra("image", model.getImage());
                intent.putExtra("price", model.getPrice());
                intent.putExtra("desc", model.getDescription());
                intent.putExtra("name", model.getName());
                intent.putExtra("type", 1);

                // start the intent
                context.startActivity(intent);

               // holder.itemview.context.startActivity(intent);
                //context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size(); // size of the recycler view (number of items present)
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView mainName, price, description;

        CardView mainCardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            mainCardView = itemView.findViewById(R.id.mainCardView);

            foodimage = itemView.findViewById(R.id.imageView);
            mainName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.orderPrice);
            description = itemView.findViewById(R.id.foodDescription);
        }

    }
}
