package com.example.project_mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalcode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore =FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city=findViewById(R.id.ad_city);
        phoneNumber=findViewById(R.id.ad_phone);
        postalcode=findViewById(R.id.ad_code);
        addAddressBtn =findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalcode.getText().toString();
                String userNumber = phoneNumber.getText().toString();

                String final_address = "";
                if (!userName.isEmpty()) {
                    final_address += userName;
                }
                if (!userCity.isEmpty()) {
                    final_address += userCity;
                }
                if (!userAddress.isEmpty()) {
                    final_address += userAddress;
                }
                if (!userCode.isEmpty()) {
                    final_address += userCode;
                }
                if (!userNumber.isEmpty()) {
                    final_address += userNumber;
                }

                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {

                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("userAddress", final_address);

                    firestore.collection("Address").document(auth.getCurrentUser().getUid())
                            .collection("CurrentUser").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NotNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddAddressActivity.this, "Added Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAddressActivity.this, AddressActivity.class));
                                finish();
                            }

                        }

                    });

                } else {
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Filed", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void setSupportActionBar(Toolbar toolbar){
    }
}