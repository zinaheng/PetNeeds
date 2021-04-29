package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView imgView;
    TextView nameView;
    RatingBar rtBar;
    TextView adView;
    TextView phView;
    TextView hView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgView =findViewById(R.id.imgView);
        nameView = findViewById(R.id.nameView);
        rtBar = findViewById(R.id.rtBar);
        adView = findViewById(R.id.adView);
        phView = findViewById(R.id.phView);
        hView = findViewById(R.id.hView);


        String name = getIntent().getStringExtra("name");
        String vicinity = getIntent().getStringExtra("vicinity");
        nameView.setText(name);
        adView.setText(vicinity);


    }
}