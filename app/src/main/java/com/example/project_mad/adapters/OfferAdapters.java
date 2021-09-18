package com.example.project_mad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_mad.R;
import com.example.project_mad.models.OfferModel;
import com.example.project_mad.models.PopularModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OfferAdapters extends RecyclerView.Adapter<OfferAdapters.ViewHolder>{

    private Context context;
    private List<OfferModel> offerModelList;

    public OfferAdapters(Context context, List<OfferModel> offerModelList) {
        this.context = context;
        this.offerModelList = offerModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(offerModelList.get(position).getImg_url()).into(holder.offImg);
        holder.discount.setText(offerModelList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return offerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView offImg;
        TextView discount;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            offImg = itemView.findViewById(R.id.off_img);
            discount = itemView.findViewById(R.id.off_dis);
        }
    }
}
