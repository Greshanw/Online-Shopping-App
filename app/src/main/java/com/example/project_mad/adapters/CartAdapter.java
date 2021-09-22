package com.example.project_mad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_mad.R;
import com.example.project_mad.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageTask;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {

    Context context;
    List<CartModel> cartModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int count;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
         this.context = context;
         this.cartModelList = cartModelList;
         firestore = FirebaseFirestore.getInstance();
         auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.price.setText(String.valueOf(cartModelList.get(position).getProductPrice()));
        holder.itemName.setText(String.valueOf(cartModelList.get(position).getProductName()));
        holder.qty.setText(String.valueOf(cartModelList.get(position).getTotalQuantity()));
        Glide.with(context).load(cartModelList.get(position).getProductImg()).into(holder.imageView);

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Product removed from the Cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        count = getItemCount();
        Intent intent = new Intent("cartlisttot");
        intent.putExtra("totwish", count);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView itemName;
        TextView price;
        ImageView delBtn;
        TextView qty;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
            itemName = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            delBtn = itemView.findViewById(R.id.del_btn);
        }
    }
}
