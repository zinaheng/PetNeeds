package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.petneeds.adapters.PetAdapter;
import com.example.petneeds.models.PetInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MainActivity extends FragmentActivity implements OnMapReadyCallback,AdapterView.OnItemSelectedListener {


    public static final String TAG = "MainActivity";
    static Double lng;
    static Double lat;
    static String name;
    List<PetInfo> info;
    private Spinner sp;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView rvPetDetails = findViewById(R.id.rvPetDetails);
//        final EditText enteredZip = (EditText) findViewById(R.id.etZipcode);
//        ImageButton search = findViewById(R.id.searchbutton);
        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//below is the code for setting up the drop down menu
        sp = (Spinner) findViewById(R.id.drSpin);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);


        sp.setOnItemSelectedListener(this);
        //    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent){
//
//    }
//
//}



        info = new ArrayList<>();
        final PetAdapter petAdapter = new PetAdapter(this, info);
        rvPetDetails.setLayoutManager(new LinearLayoutManager(this));
        rvPetDetails.setAdapter(petAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                final String ZIPCODE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";
                /*List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }*/

                final AsyncHttpClient client = new AsyncHttpClient();

                client.get(ZIPCODE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("Lat", String.valueOf(lat));
                        final String PET_INFORMATION = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng +
                                "&radius=15000&type=pet_store&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";
                        client.get(PET_INFORMATION, new JsonHttpResponseHandler() {
                            public void onSuccess(int i, Headers headers, JSON json) {
                                JSONObject jsonObject = json.jsonObject;
                                try {
                                    JSONArray results = jsonObject.getJSONArray("results");

                                    for ( int j = 0 ; j <= 5 ; j++) {
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
                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                    }

                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
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