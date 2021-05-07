package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
//      double
        PetInfo pInfo = Parcels.unwrap(getIntent().getParcelableExtra("pInfo"));
        nameView.setText(name);
        adView.setText(vicinity);
////        String name = getIntent().getStringExtra("name");
//        String vicinity = getIntent().getStringExtra("vicinity");
//        nameView.setText(petInfo.getName());
//        adView.setText(petInfo.getVicinity());
//        rtBar.setRating((float) rating);
        rtBar.setRating((float) pInfo.getRating());
//        imgView.setText
//        String photos = getIntent().getStringExtra("photos");


    }
}