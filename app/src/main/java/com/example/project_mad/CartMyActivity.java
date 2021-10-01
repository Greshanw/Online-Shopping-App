package com.example.project_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_mad.adapters.cart_my_Adapter;
import com.example.project_mad.models.cart_my_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartMyActivity extends AppCompatActivity {
    int overAllTotalAmount;
    TextView overAllAmount;
    Button orderPlace;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<cart_my_Model> cart_my_modelList;
    cart_my_Adapter cart_my_adapter;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_my);

        auth =FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        toolbar=findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get data from my cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReciver,new IntentFilter("MyTotalAmount"));

        recyclerView=findViewById(R.id.cart_rec);
        orderPlace = findViewById(R.id.place_order);
        overAllAmount = findViewById(R.id.totalAmount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cart_my_modelList=new ArrayList<>();
        cart_my_adapter=new cart_my_Adapter(this,cart_my_modelList);
        recyclerView.setAdapter(cart_my_adapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        cart_my_Model cart_my_Model = documentSnapshot.toObject(cart_my_Model.class);
                        cart_my_modelList.add(cart_my_Model);
                        cart_my_adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        orderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartMyActivity.this,AddressActivity.class));
            }
        });
    }

    public void setSupportActionBar(Toolbar toolbar) {
    }

    public BroadcastReceiver mMessageReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String totalBill = intent.getStringExtra("total");
            overAllAmount.setText("Total Amount : Rs."+totalBill);
        }
    };
}
