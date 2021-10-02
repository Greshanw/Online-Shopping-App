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

import com.example.project_mad.adapters.CatAdapter;
import com.example.project_mad.adapters.HomeAdapter;
import com.example.project_mad.adapters.OfferAdapters;
import com.example.project_mad.adapters.RecommendedAdapter;
import com.example.project_mad.models.CatModel;
import com.example.project_mad.models.HomeCategory;
import com.example.project_mad.models.OfferModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class categoryFragment extends Fragment {

    //initialize variables
    RecyclerView cat_rec;
    FirebaseFirestore db;

    //categories
    List<CatModel> cateList;
    CatAdapter catAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //assign variables
        View root = inflater.inflate(R.layout.fragment_category,container,false);


        cat_rec= root.findViewById(R.id.cat_rec);
        db = FirebaseFirestore.getInstance();

        //category items
        cat_rec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        cateList = new ArrayList<>();
        catAdapter = new CatAdapter(getActivity(),cateList);
        cat_rec.setAdapter(catAdapter);



        //listener
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CatModel homeCategory = document.toObject(CatModel.class);
                                cateList.add(homeCategory);
                                catAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }
}