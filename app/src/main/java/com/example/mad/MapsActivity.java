package com.example.mad;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           Intent mapsIntent = new Intent(getApplicationContext(), homepage.class);
                                           startActivity(mapsIntent);
                                       }
                                       });
    }

    //TODO: Gps tracker, Autocomplete places to search for locations, Geofence, Alarm

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(15.0f);
        getLocation();
    }


    //adds a 5km radius
    public void addCircle(double lt, double lng){
        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng( lt, lng))
                .radius(5000) // In meters
                .strokeWidth(2)
                .strokeColor(Color.argb(255, 35, 121, 131))
                .fillColor(Color.argb(30, 35,121,131));
        // Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);
    }

    //adds a marker to the location
    public void addMarker(double lt, double lng){

        LatLng location = new LatLng(lt, lng);
        mMap.addMarker(new MarkerOptions().position(location).title("5km radius from location" + lat + lon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private void getLocation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("user", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lat = document.getDouble("Lat");
                                lon = document.getDouble("Lon");
                                addCircle(lat,lon);
                                addMarker(lat,lon);

                            }
                        } else {
                            Intent setup = new Intent(getApplicationContext(), setLocation.class);
                            startActivity(setup);
                            Toast.makeText(getApplicationContext(),"Please set up your details",Toast.LENGTH_LONG).show();
                        } }});
    }
}
