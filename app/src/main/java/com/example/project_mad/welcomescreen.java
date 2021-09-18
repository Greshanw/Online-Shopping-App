package com.example.project_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project_mad.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class welcomescreen extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        auth = FirebaseAuth.getInstance();
        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        if(auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(welcomescreen.this, NavigationbarActivity.class));
            Toast.makeText(welcomescreen.this, "Wait!! you are already logged in." , Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void registration(View view) {
        startActivity(new Intent(welcomescreen.this, registration.class));
    }

    public void login(View view) {
        startActivity(new Intent(welcomescreen.this, login.class));
    }
}