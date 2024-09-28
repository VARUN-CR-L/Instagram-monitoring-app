package com.example.im;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private ImageView pr;
    private ImageView pu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pr = findViewById(R.id.imageView);
        pu = findViewById(R.id.imageView2);

        pr.setOnClickListener(view -> {
            Intent i = new Intent(this, PrivateProfileActivity.class);
            startActivity(i);
        });

        pu.setOnClickListener(view -> {
            Intent i = new Intent(this, PublicProfileActivity.class);
            startActivity(i);
        });






    }
}