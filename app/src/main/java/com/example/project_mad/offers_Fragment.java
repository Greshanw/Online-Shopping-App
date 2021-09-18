package com.example.project_mad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_mad.adapters.OfferAdapters;
import com.example.project_mad.adapters.PopularAdapters;
import com.example.project_mad.models.OfferModel;
import com.example.project_mad.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class offers_Fragment extends Fragment {

    RecyclerView offer_rec;
    FirebaseFirestore db;

    List<OfferModel> offerModelList;
    OfferAdapters offerAdapters;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offers_,container,false);
        db = FirebaseFirestore.getInstance();

        offer_rec= root.findViewById(R.id.offer_rec);

        //popular items
        offer_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        offerModelList = new ArrayList<>();
        offerAdapters = new OfferAdapters(getActivity(),offerModelList);
        offer_rec.setAdapter(offerAdapters);

        db.collection("OffersProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OfferModel offerModel = document.toObject(OfferModel.class);
                                offerModelList.add(offerModel);
                                offerAdapters.notifyDataSetChanged();


                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}