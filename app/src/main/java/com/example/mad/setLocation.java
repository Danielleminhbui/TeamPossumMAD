package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class setLocation extends AppCompatActivity {

    private static final String TAG = "";
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private FloatingActionButton findLocation;

    private TextInputLayout nameInput;

    EditText locationInput;

    //input address into location input
    private Geocoder geocoder;
    private List<Address> address;
    private GeoPoint geoPoint;
    private double lat;
    private double lon;


    //This activity is supposed to go after setting up (this is phase 2 of setting up to get user to add in their location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        nameInput = findViewById(R.id.nameInput);
        //Finding user location

        locationInput = findViewById(R.id.locationInput);
        findLocation = (FloatingActionButton) findViewById(R.id.recentLocation);
        findLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!checkPermissions())
                {
                    Log.d(TAG, "required to request permissions");
                    requestPermissions();
                } else {
                    getLastLocation();
                }}});
        Button confirmBtn = (Button) findViewById(R.id.setupButton);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                completeSetup();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void completeSetup(){
        if(validateData()) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userid = user.getUid();

            //Adding data into map to put into firestore
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("Name", nameInput.getEditText().getText().toString());
            userInfo.put("Location", locationInput.getText().toString());
            userInfo.put("Lat", lat);
            userInfo.put("Lon", lon);
            userInfo.put("user", userid.toString());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .add(userInfo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "User added " + documentReference.getId());
                            //Will eventually link to ViewEvent.class;
                            Intent intent = new Intent(setLocation.this, homepage.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error", e);
                        }
                    });
        }}


    private boolean validateData(){
        boolean verified = false;
        //Validate if user entered a name
        if(!(nameInput.getEditText().getText().toString().length() >0)){
            verified = false;
            nameInput.setError(getResources().getString(R.string.name_error));
        }
        //Validate if user has entered location
        if(!(locationInput.getText().toString().length() > 0)){
            verified = false;
            locationInput.setError(getResources().getString(R.string.location_error));
        }
        else{
            verified = true;
        }
        return verified;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(setLocation.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            try {
                                Geocoder geo = new Geocoder(setLocation.this, Locale.getDefault());
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                                address = geo.getFromLocation(lat,lon, 1);
                                locationInput.setText(address.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"no_location_detected",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "checking permissions");
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(setLocation.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        Log.d(TAG, "requesting permissions");
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.d(TAG, "Displaying permission rationale to provide additional context.");

            // Dialog Pop-up
            AlertDialog.Builder builder = new AlertDialog.Builder(setLocation.this);
            builder.setTitle("Permissions")
                    .setMessage("Location permission is needed for core functionality")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startLocationPermissionRequest();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(setLocation.this,"Permission was denied, but is needed for core functionality",Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog dialog  = builder.create();
            dialog.show();

        } else {
            Log.d(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.d(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
                Log.d(TAG, "permissions granted");
            } else {
                // Permission denied.
                Log.d(TAG, "permissions denied");
                Toast.makeText(setLocation.this,"Permission was denied, but is needed for core functionality",Toast.LENGTH_SHORT).show();
            }
        }
    }




    }