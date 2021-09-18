package com.example.project_mad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_mad.adapters.MyWishListAdapter;
import com.example.project_mad.adapters.PopularAdapters;
import com.example.project_mad.models.MyWishListModel;
import com.example.project_mad.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class wishlistFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth auth;
    int count = 0;
    TextView countt ;

    RecyclerView recyclerView;
    MyWishListAdapter myWishListAdapter;
    List<MyWishListModel> myWishListModelList;

    public wishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_wishlist, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerview);
        countt = root.findViewById(R.id.wishcount);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("wishlisttot"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView .VERTICAL,false));

        myWishListModelList = new ArrayList<>();
        myWishListAdapter = new MyWishListAdapter(getActivity(), myWishListModelList);
        recyclerView.setAdapter(myWishListAdapter);

        db.collection("WishList").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                String documentId = document.getId();

                                MyWishListModel myWishListModel = document.toObject(MyWishListModel.class);
                                myWishListModel.setDocumentId(documentId);


                                myWishListModelList.add(myWishListModel);
                                myWishListAdapter.notifyDataSetChanged();



                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return  root;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int counttot = intent.getIntExtra("totwish", 0);
            countt.setText(String.valueOf(counttot));
        }
    };
}