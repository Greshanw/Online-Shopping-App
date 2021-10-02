//message insertion part
package com.example.project_mad;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class message extends AppCompatActivity {

    //initialize variable
    EditText email,subject,message;
    Button button2;
    Button button3;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

      //assign variable
       email =(EditText) findViewById(R.id.email);
       subject =(EditText) findViewById(R.id.subject);
       message =(EditText) findViewById(R.id.message);

       button2 = (Button) findViewById(R.id.button2);

       //validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

       awesomeValidation.addValidation(this,R.id.email
       , Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        awesomeValidation.addValidation(this,R.id.email
                , RegexTemplate.NOT_EMPTY,R.string.empty_email);

       awesomeValidation.addValidation(this,R.id.subject
       , RegexTemplate.NOT_EMPTY,R.string.invalid_subject);

        awesomeValidation.addValidation(this,R.id.message
                , RegexTemplate.NOT_EMPTY,R.string.invalid_message);


        //message sent button declaration
       button2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(awesomeValidation.validate()){
                   //on success
                   Toast.makeText(getApplicationContext(),
                           "successful",Toast.LENGTH_SHORT).show();
                   inserData();
                   clearall();
               }
               //if unsuccessful
                else {
                   Toast.makeText(getApplicationContext(),
                           "validation unsuccessful",Toast.LENGTH_SHORT).show();
               }
           }
       });

       //sent message button declaration
         button3 = (Button)findViewById(R.id.button3);
         button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(message.this,messagelist.class);
                startActivity(intent);
            }
        });


    }
    //mapping rows and insert data into database
      private void inserData(){
        Map<String,Object> map = new HashMap<>();
        map.put("email",email.getText().toString());
        map.put("subject",subject.getText().toString());
        map.put("message",message.getText().toString());

        //push data to the database.
        FirebaseDatabase.getInstance().getReference().child("customer").push()
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
    //passing fields empty after sending the message.
      private void clearall(){

        email.setText("");
        subject.setText("");
        message.setText("");
    }

}