package com.example.project_mad;

import com.google.firebase.firestore.CollectionReference;

public class firebaseFirestore {
    private static firebaseFirestore instance;

    public static firebaseFirestore getInstance() {
        return instance;
    }

    public static void setInstance(firebaseFirestore instance) {
        firebaseFirestore.instance = instance;
    }

    public  CollectionReference collection(String currentUSer){
        return null;
    }
}
