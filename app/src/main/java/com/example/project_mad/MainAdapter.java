package com.example.project_mad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.myViewHolder> {

    Context context;
    ArrayList<com.example.project_mad.MainModel> list;

    public MainAdapter(Context context, ArrayList<MainModel> list) {
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

     MainModel mainmodel =list.get(position);
     holder.email.setText(mainmodel.getEmail());
     holder.subject.setText(mainmodel.getSubject());
     holder.message.setText(mainmodel.getMessage());


   /* holder.deletebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.email.getContext());
            builder.setTitle("are you sure?");
            builder.setMessage("after deleting can't be undone.");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("customer");
                    //.child(getRef(position).getKey()).removeValue();
                }
            });

            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(holder.email.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
         TextView email, subject, message;

         Button deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            subject =itemView.findViewById(R.id.subject);
            message = itemView.findViewById(R.id.message);

            deletebtn =(Button) itemView.findViewById(R.id.deletebtn);

        }

    }

}
