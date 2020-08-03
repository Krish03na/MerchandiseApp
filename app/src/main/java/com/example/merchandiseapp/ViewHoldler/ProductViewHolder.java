package com.example.merchandiseapp.ViewHoldler;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchandiseapp.Interface.ItemClickListener;
import com.example.merchandiseapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtname, txtDescription,txtprice;
    public ImageView imgproduct;
    public ItemClickListener listener;


    public ProductViewHolder(View itemView){
        super(itemView);

        imgproduct = (ImageView) itemView.findViewById(R.id.product_image);
        txtname = (TextView) itemView.findViewById(R.id.product_head);
        txtDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtprice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
         listener.onClick(v,getAdapterPosition(),false);
    }
}
