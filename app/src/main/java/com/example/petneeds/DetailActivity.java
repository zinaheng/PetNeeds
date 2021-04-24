package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView imgView;
    TextView nameView,adView,phView,hView;
    RatingBar rtBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgView =findViewById(R.id.imgView);
        rtBar = findViewById(R.id.rtBar);
        nameView = findViewById(R.id.nameView);
        adView = findViewById(R.id.adView);
        phView = findViewById(R.id.phView);
        hView = findViewById(R.id.hView);
    }
}