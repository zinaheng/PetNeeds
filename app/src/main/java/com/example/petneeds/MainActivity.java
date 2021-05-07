package com.example.petneeds;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.petneeds.adapters.PetAdapter;
import com.example.petneeds.models.PetInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationListener;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback,AdapterView.OnItemSelectedListener,LocationListener {


    public static final String TAG = "MainActivity";
    static Double lng;
    static Double lat;
    static String name;
    static String type;
    static int count;
    List<PetInfo> info;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.sv_location);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                type = searchView.getQuery().toString();
                getLocation();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MainActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            info = new ArrayList<>();

            RecyclerView rvPetDetails = findViewById(R.id.rvPetDetails);
            rvPetDetails.setLayoutManager(new LinearLayoutManager(this));
            final PetAdapter petAdapter = new PetAdapter(this, info);
            rvPetDetails.setAdapter(petAdapter);


            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


            final AsyncHttpClient client = new AsyncHttpClient();

            final String PET_INFORMATION = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," + location.getLongitude() +
                    "&radius=15000&name=" + type +"&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";

            Log.i("URL: ", PET_INFORMATION);
            client.get(PET_INFORMATION, new JsonHttpResponseHandler() {
                public void onSuccess(int i, Headers headers, JSON json) {
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");

                        int arlen = results.length();

                        count = arlen < 5 ?  arlen : 5;
                        map.clear();
                        for (int j = 0; j < count; j++) {
                            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(j)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(j)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");
                            name = ((JSONArray) jsonObject.get("results")).getJSONObject(j)
                                    .getString("name");
                            LatLng latLng = new LatLng(lat, lng);
                            map.addMarker(new MarkerOptions().position(latLng).title(name));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }


                        Log.i(TAG, "RESULTS:" + results.toString());
                        info.clear();
                        info.addAll(PetInfo.fromJsonArray(results));
                        petAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}