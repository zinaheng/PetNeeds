package com.example.petneeds.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import okhttp3.Headers;

@Parcel
public class PetInfo {
    String name;
    String vicinity;
    String photos;
    double rating;
    String hours;
    String photoRef;
    String idRef;
    String monday;

    public PetInfo() {
    }


    public PetInfo(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        vicinity = jsonObject.getString("vicinity");
        rating = jsonObject.getDouble("rating");


        if(jsonObject.has("photos")){
            photoRef =jsonObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
        }else{
            photoRef= "ATtYBwJxNpa9AS2fVJBMJryyIeV-e_UlyfmeAmrkFQ999zsADk-lLhV2SBWXqQpO88Ppo9laJNzWY4xxbQsRvuBBeCe_bLCbzEwHDb2IGRsOk48JXViFIOvH9yQycFv5mrXtApsGFQjkoW60juWX8QhE68ACfCJBJYy3RGYPtDzVu0Rjq4v7";
        }

        idRef = jsonObject.getString("place_id");


    }


    public static List<PetInfo> fromJsonArray(JSONArray petJsonArray) throws JSONException {
        List<PetInfo> info = new ArrayList<>();
        for (int i = 0; i < petJsonArray.length(); i++) {
            info.add(new PetInfo(petJsonArray.getJSONObject(i)));
        }
        return info;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public double getRating() {
        return rating;
    }

    public String getPhotos() {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoRef +"&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";
    }

    public String getHours() {
        return idRef;
    }
}

