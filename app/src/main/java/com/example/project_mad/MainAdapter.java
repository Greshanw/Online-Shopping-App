package com.example.project_mad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.myViewHolder> {

    Context context;
    ArrayList<com.example.project_mad.MainModel> list;

    public MainAdapter(Context context, ArrayList<com.example.project_mad.MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item,parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

     com.example.project_mad.MainModel mainmodel =list.get(position);
     holder.email.setText(mainmodel.getEmail());
     holder.subject.setText(mainmodel.getSubject());
     holder.message.setText(mainmodel.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
         TextView email, subject, message;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            subject =itemView.findViewById(R.id.subject);
            message = itemView.findViewById(R.id.message);
        }


    }




}
