package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.petneeds.models.PetInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    ImageView imgView;
    TextView nameView;
    RatingBar rtBar;
    TextView adView;
    TextView phView;
    TextView hView;
    String idRef;
    String monday;
    String phone;



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
        phView =findViewById(R.id.phView);

        String name = getIntent().getStringExtra("name");
        String vicinity = getIntent().getStringExtra("vicinity");
        PetInfo pInfo = Parcels.unwrap(getIntent().getParcelableExtra("pInfo"));
        nameView.setText(name);
        adView.setText(vicinity);
        rtBar.setRating((float) pInfo.getRating());
        idRef= pInfo.getHours();

        String imageUrl;
        imageUrl=pInfo.getPhotos();
        Glide.with(this).load(imageUrl).into(imgView);



        final AsyncHttpClient client = new AsyncHttpClient();

        final String STORE_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + idRef + "&fields=name,opening_hours,formatted_phone_number&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";

        client.get(STORE_DETAILS, new JsonHttpResponseHandler() {
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                monday = "";
                try {
                    if(jsonObject.getJSONObject("result").has("opening_hours")) {
                        for (int j = 0; j < 7; j++) {
                            monday += jsonObject.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text").getString(j);
                            monday += "@";
                        }
                    }else{
                        monday = "hours not available";
                    }

                    if(jsonObject.getJSONObject("result").has("formatted_phone_number")) {
                        phone = jsonObject.getJSONObject("result").getString("formatted_phone_number");
                    }else{
                        phone = "phone number not available";
                    }
                    Log.i("Monday: ", monday);
                    hView.setText(monday.replaceAll("@","\n"));
                    phView.setText(phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });


    }
}