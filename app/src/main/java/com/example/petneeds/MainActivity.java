package com.example.petneeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    public static final String TAG ="MainActivity";
    static Double lng;
    static Double lat;
    List<PetInfo> info;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RecyclerView rvPetDetails = findViewById(R.id.rvPetDetails);
        final EditText enteredZip = (EditText)findViewById(R.id.etZipcode);
        ImageButton search = findViewById(R.id.searchbutton);

        info = new ArrayList<>();
        final PetAdapter petAdapter = new PetAdapter(this, info);
        rvPetDetails.setLayoutManager(new LinearLayoutManager(this));
        rvPetDetails.setAdapter(petAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= enteredZip.getText().toString();
                final String ZIPCODE = "https://maps.googleapis.com/maps/api/geocode/json?address=" +value+"&key=AIzaSyA3eIDiQk5MNmPvyx62qERmyb54UzORsIg";
                final AsyncHttpClient client = new AsyncHttpClient();

                client.get(ZIPCODE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
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
                                    Log.i(TAG, "RESULTS:" + results.toString());
                                    info.clear();
                                    info.addAll(PetInfo.fromJsonArray(results));
                                    petAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                    }
                });
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng Dallas = new LatLng(32.789444,96.791131);
        map.addMarker(new MarkerOptions().position(Dallas).title("Dallas"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Dallas));
    }
    }
