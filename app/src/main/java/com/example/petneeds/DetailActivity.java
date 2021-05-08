package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.petneeds.models.PetInfo;

import org.parceler.Parcels;

import org.parceler.Parcels;

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
        String open_now = String.valueOf((getIntent().getStringExtra("open_now")));
        PetInfo pInfo = Parcels.unwrap(getIntent().getParcelableExtra("pInfo"));
        nameView.setText(name);
        adView.setText(vicinity);
        rtBar.setRating((float) pInfo.getRating());
        hView.setText(open_now);
//        String imageUrl;
//        imageUrl=pInfo.getPhotos();
//        Glide.with(this).load(imageUrl).into(imgView);

        String imageUrl;
        imageUrl=pInfo.getPhotos();
        Glide.with(this).load(imageUrl).into(imgView);

        String photos = getIntent().getStringExtra("photos");



    }
}