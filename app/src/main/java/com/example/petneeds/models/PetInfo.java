package com.example.petneeds.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class PetInfo {
    String name;
    String vicinity;
    String photos;
    double rating;
    String hours;

    public PetInfo() {
    }


    public PetInfo(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        vicinity = jsonObject.getString("vicinity");
        photos = jsonObject.getString("photos");
        rating = jsonObject.getDouble("rating");

        hours = String.valueOf(jsonObject.get("opening_hours").equals("open_now"));


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
        return String.format("https://maps.google.com/maps/contrib/w342/%s",photos);
    }

    public String getHours() {
        if(hours == "true"){
            return "open now";
        }
        else {
            return "close now";
        }
    }
}




