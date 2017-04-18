package com.example.happy.mycreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Item> itemList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,price,qty;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.rvTitle);
            price = (TextView) view.findViewById(R.id.rvPrice);
            qty = (TextView) view.findViewById(R.id.rvQuantity);
        }
    }

    public Adapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_recylerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.qty.setText(item.getQty());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}