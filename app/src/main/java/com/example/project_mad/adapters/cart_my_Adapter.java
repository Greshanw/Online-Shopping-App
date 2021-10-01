package com.example.project_mad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_mad.R;
import com.example.project_mad.models.CartModel;
import com.example.project_mad.models.RecommendedModel;
import com.example.project_mad.models.cart_my_Model;

import org.jetbrains.annotations.NotNull;

import java.text.BreakIterator;
import java.util.List;

public class cart_my_Adapter extends RecyclerView.Adapter<cart_my_Adapter.Viewholder> {

        Context context;
        List<cart_my_Model> list;
        int totalAmount = 0;

        public cart_my_Adapter(Context context, List<cart_my_Model>list){
            this.context=context;
            this.list=list;
        }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"Rs.");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());

        //Total amount pass to cart Activity
        totalAmount=totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmout");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        }

    @Override
    public int getItemCount() {return list.size();}

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, totalQuantity, totalPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}








