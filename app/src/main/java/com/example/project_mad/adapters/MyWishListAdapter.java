package com.example.project_mad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_mad.ProductActivity;
import com.example.project_mad.R;
import com.example.project_mad.models.MyWishListModel;
import com.example.project_mad.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MyWishListAdapter  extends RecyclerView.Adapter<MyWishListAdapter.ViewHolder> {
    Context context;
    List<MyWishListModel> wishListModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int count = 0;

    int totQ = 1;
    int totalPrice = 0;


    public MyWishListAdapter(Context context, List<MyWishListModel> wishListModelList) {
        this.context = context;
        this.wishListModelList = wishListModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_wishlist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.price.setText((String.valueOf(wishListModelList.get(position).getProductPrice())));
        Glide.with(context).load(wishListModelList.get(position).getProductImg()).into(holder.imageView);

        holder.deleteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("WishList").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser")
                        .document(wishListModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    wishListModelList.remove(wishListModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Product removed from the WishList", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        holder.atcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }

            private void addedToCart() {
                String saveCurrentDate, saveCurrentTime;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format((calForDate.getTime()));

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", wishListModelList.get(position).getProductName());
                cartMap.put("productImg", wishListModelList.get(position).getProductImg());
                cartMap.put("productPrice", holder.price.getText().toString());
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalQuantity", holder.quant.getText().toString());
                cartMap.put("totalPrice", totalPrice);

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });
            }
        });

        holder.addq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totQ<15){
                    totQ++;
                    holder.quant.setText(String.valueOf(totQ));
                    totalPrice = Integer.parseInt(wishListModelList.get(position).getProductPrice()) * totQ;
                }
            }
        });
        holder.removq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totQ> 0){
                    totQ--;
                    holder.quant.setText(String.valueOf(totQ));
                    // totalPrice = viewAllModel.getPrice() * totQ;
                    totalPrice = Integer.parseInt(wishListModelList.get(position).getProductPrice()) * totQ;
                }
            }
        });

        count = getItemCount();
        Intent intent =  new Intent("wishlisttot");
        intent.putExtra("totwish", count);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price;
        TextView quant;
        ImageView deleteI;
        ImageView addq, removq;
        Button atcart;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wish_img);
            price=itemView.findViewById(R.id.wish_price);
            deleteI= itemView.findViewById(R.id.wishdelete);
            addq = itemView.findViewById(R.id.add_item);
            removq = itemView.findViewById(R.id.remove_item);
            atcart = itemView.findViewById(R.id.addtocart);
            quant = itemView.findViewById(R.id.quantity);

        }
    }
}
