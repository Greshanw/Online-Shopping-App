package com.example.project_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CreditCardOptionActivity extends AppCompatActivity {

    String total;
    Button buynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_option);

        buynow = findViewById(R.id.btnBuy);

        Intent intenttotal = getIntent();
        total = intenttotal.getStringExtra("amount");

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreditCardOptionActivity.this,PaymentActivity.class);
                intent.putExtra("amount",total);
                startActivity(intent);
            }
        });
    }
}