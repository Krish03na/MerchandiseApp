package com.example.merchandiseapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merchandiseapp.R;
import com.example.merchandiseapp.model.Productdata;

import java.util.List;
import java.util.zip.Inflater;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerviewholder>{

    Context context;
    List<Productdata> productlist;

    public recyclerAdapter(Context context, List<Productdata> productlist) {
        this.context = context;
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public recyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);

        return new recyclerviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewholder holder, int position) {
         holder.prod_name.setText(productlist.get(position).getName());
         holder.prod_price.setText(productlist.get(position).getPrice());
         //a new iamge url data type is to be set in the model product data
         //holder.image.setImageResource(productlist.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public static final class recyclerviewholder extends  RecyclerView.ViewHolder{

        ImageView image;
        TextView prod_name,prod_price;
        public recyclerviewholder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            prod_name = itemView.findViewById(R.id.product_name);
            prod_price = itemView.findViewById(R.id.price);
        }
    }


}
