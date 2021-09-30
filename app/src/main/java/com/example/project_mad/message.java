package com.example.project_mad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class message extends AppCompatActivity {

    EditText email,subject,message;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);


       email =(EditText) findViewById(R.id.email);
       subject =(EditText) findViewById(R.id.subject);
       message =(EditText) findViewById(R.id.message);

       button2 = (Button) findViewById(R.id.button2);

       button2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               inserData();
               clearall();
           }
       });




    }
    private void inserData(){
        Map<String,Object> map = new HashMap<>();
        map.put("email",email.getText().toString());
        map.put("subject",subject.getText().toString());
        map.put("message",message.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("fly buy").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(message.this,"message sent!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(message.this, "insertion failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearall(){

        email.setText("");
        subject.setText("");
        message.setText("");
    }
}