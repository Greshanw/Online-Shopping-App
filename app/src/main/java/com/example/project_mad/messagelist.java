package com.example.project_mad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class messagelist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    com.example.project_mad.MainAdapter mainadapter;
    ArrayList<com.example.project_mad.MainModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelist);

        recyclerView = findViewById(R.id.ml);
        database = FirebaseDatabase.getInstance().getReference(  "customer");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        mainadapter = new com.example.project_mad.MainAdapter(this,list);
        recyclerView.setAdapter(mainadapter);


        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   com.example.project_mad.MainModel mainmodel = dataSnapshot.getValue(com.example.project_mad.MainModel.class);
                   list.add(mainmodel);
               }
               mainadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}