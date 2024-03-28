package com.f126219.recordarcheryscoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class roundInformation extends AppCompatActivity {

    View roundInfoPage;
    private LocationRequest locationRequest;
    String city;
    ImageView profileImage, trophyImage;
    Button profileButton, recordsButton, startButton;
    EditText location, date;

    //OVERVIEW: Activity for the user to customise the round before they begin scoring

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_information);

        //Navbar Setup

        //Navbar Profile Activity
        profileImage = findViewById(R.id.profile);
        profileButton = findViewById(R.id.profileButton);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });

        //Navbar Records Activity
        trophyImage = findViewById(R.id.trophy);
        recordsButton = findViewById(R.id.recordsButton);
        trophyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intent);
                finish();
            }
        });
        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        //Responding to swipe touch events
        roundInfoPage = findViewById(R.id.roundInfoPage);
        roundInfoPage.setOnTouchListener(new OnSwipeTouchListener(roundInformation.this) {
            public void onSwipeRight() {
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intent);
                finish();
            }
        });

        roundInfoPage.setOnTouchListener(new OnSwipeTouchListener(roundInformation.this) {
            public void onSwipeLeft() {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });

        roundInfoPage.setOnTouchListener(new OnSwipeTouchListener(roundInformation.this) {
            public void onSwipeTop() {
                startRound();
            }
        });

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences(
                getString(R.string.shared_preferences_key), MODE_PRIVATE);
        if (preferences.getInt("Location Permission", 0) == 1){ //Location permission enabled

            location = findViewById(R.id.location);
            getCurrentLocation(); //writes current location to shared preferences

            String currentLocation = preferences.getString("Fetched Location", null);

            if (currentLocation != null) {
                Log.i("Current Location", currentLocation);
                location.setText(currentLocation);
            } else {
                Toast.makeText(roundInformation.this, "An Error Occurred Fetching Location", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(roundInformation.this, "Location Not Enabled", Toast.LENGTH_LONG).show();
        }

        //Button to start round
        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRound();
            }
        });
    }

    public void startRound(){
        //All information must be entered before round can begin
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        String currentLocation = location.getText().toString();
        String currentDate = date.getText().toString();

        RadioGroup roundSelection = findViewById(R.id.roundGroup);
        RadioGroup bowStyleSelection = findViewById(R.id.bowStyleGroup);

        if (TextUtils.isEmpty(currentLocation) || TextUtils.isEmpty(currentDate)){
            Toast.makeText(roundInformation.this, "Enter Location and Date", Toast.LENGTH_LONG).show();
            return;
        }

        if (roundSelection.getCheckedRadioButtonId() == -1){
            Toast.makeText(roundInformation.this, "Select Round", Toast.LENGTH_LONG).show();
            return;
        }else if (bowStyleSelection.getCheckedRadioButtonId() == -1){
            Toast.makeText(roundInformation.this, "Select Bow Style", Toast.LENGTH_LONG).show();
            return;
        }

        //Writes round information to shared preferences
        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences(
                getString(R.string.shared_preferences_key), MODE_PRIVATE);

        String currentRound;
        RadioButton Portsmouth = findViewById(R.id.Portsmouth);
        RadioButton WA18 = findViewById(R.id.WA18);
        RadioButton Worcester = findViewById(R.id.Worcester);
        if (Portsmouth.isChecked()){
            currentRound = "Portsmouth";
        }else if (WA18.isChecked()){
            currentRound = "WA18";
        }else{
            currentRound = "Worcester";
        }
        String currentBowStyle;
        RadioButton Recurve = findViewById(R.id.Recurve);
        RadioButton Barebow = findViewById(R.id.Barebow);
        RadioButton Compound = findViewById(R.id.Compound);
        if (Recurve.isChecked()){
            currentBowStyle = "Recurve";
        }else if (Barebow.isChecked()){
            currentBowStyle = "Barebow";
        }else{
            currentBowStyle = "Compound";
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentScoringLocation", location.getText().toString());
        editor.putString("currentScoringDate", date.getText().toString());
        editor.putString("currentScoringRound", currentRound);
        editor.putString("currentScoringBowStyle", currentBowStyle);
        editor.apply();

        //Starts round
        Intent intent = new Intent(getApplicationContext(), Scoring.class);
        startActivity(intent);
        finish();
    }


    //Gets device current location and writes to shared preferences
    public void getCurrentLocation(){
        city = null;

        locationRequest = locationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(roundInformation.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            LocationServices.getFusedLocationProviderClient(roundInformation.this)
                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            LocationServices.getFusedLocationProviderClient(roundInformation.this).removeLocationUpdates(this);

                            if (locationResult != null && locationResult.getLocations().size() > 0){

                                int index = locationResult.getLocations().size() -1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longitude = locationResult.getLocations().get(index).getLongitude();
                                Log.i("Location retrieved", String.valueOf(latitude) + String.valueOf(longitude));

                                //convert to town/city location
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(roundInformation.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    city = addresses.get(0).getLocality();
                                    Log.i("Location retrieved", city);

                                    SharedPreferences preferences = getSharedPreferences(
                                            getString(R.string.shared_preferences_key), MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();

                                    editor.putString("Fetched Location", city);
                                    editor.apply();

                                } catch (IOException e) {
                                    Log.e("IOException fetching location", String.valueOf(e));
                                    city = null;
                                }
                            }
                        }
                    }, Looper.getMainLooper());
        }
    }
}