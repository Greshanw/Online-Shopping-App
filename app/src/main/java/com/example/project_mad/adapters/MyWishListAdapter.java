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
import com.example.project_mad.models.MyWishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyWishListAdapter  extends RecyclerView.Adapter<MyWishListAdapter.ViewHolder> {
    Context context;
    List<MyWishListModel> wishListModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    int count = 0;

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
        ImageView deleteI;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wish_img);
            price=itemView.findViewById(R.id.wish_price);
            deleteI= itemView.findViewById(R.id.wishdelete);
        }
    }
}
