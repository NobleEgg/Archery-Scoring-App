package com.f126219.recordarcheryscoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;

public class Profile extends AppCompatActivity {

    private LocationRequest locationRequest;
    View profilePage;
    ImageView targetImage, trophyImage;
    Button scoreButton, recordsButton, sessionTimeButton;
    EditText sessionTime;

    Switch locationPerm, cameraPerm, notificationPerm;

    //OVERVIEW: Profile page activity, allows users to change camera, location and notification permissions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Navbar setup

        //Navbar Scoring Activity
        targetImage = findViewById(R.id.target);
        scoreButton = findViewById(R.id.scoreButton);
        targetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), roundInformation.class);
                startActivity(intent);
                finish();
            }
        });
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), roundInformation.class);
                startActivity(intent);
                finish();
            }
        });

        //Navbar Homepage Activity
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
        profilePage = findViewById(R.id.profilePage);
        profilePage.setOnTouchListener(new OnSwipeTouchListener(Profile.this) {
            public void onSwipeRight() {
                Intent intent = new Intent(getApplicationContext(), roundInformation.class);
                startActivity(intent);
                finish();
            }
        });

        Button userGuide = findViewById(R.id.userGuide);
        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), userGuide.class);
                startActivity(intent);
                finish();
            }
        });

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences(
                getString(R.string.shared_preferences_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        //Notifications permissions
        notificationPerm = findViewById(R.id.notificationsSwitch);

        //Set the state of the switch
        int notifPermission = preferences.getInt("Notification Permission", 0);
        if (notifPermission == 0){
            notificationPerm.setChecked(false);
        }else{
            notificationPerm.setChecked(true);
        }

        notificationPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationPerm.isChecked()){ //if notification permission enabled
                    editor.putInt("Notification Permission", 1);

                    if (preferences.getString("Session Time", null) == null){ //If no time for notification has been entered perviously
                        Toast.makeText(Profile.this, "Enter a Time to be Notified", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Profile.this, "Notifications Permission Turned On", Toast.LENGTH_LONG).show();
                } else {
                    editor.putInt("Notification Permission", 0);
                    Toast.makeText(Profile.this, "Notifications Permission Turned Off", Toast.LENGTH_LONG).show();
                }
                editor.apply();
            }
        });

        //Button sets notification at the entered time
        sessionTimeButton = findViewById(R.id.SessionTimeConfirm);
        sessionTime = findViewById(R.id.sessionTime);
        sessionTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences(
                        getString(R.string.shared_preferences_key), MODE_PRIVATE);

                if (preferences.getInt("Notification Permission", 0) == 1) { //if notification permission enabled

                    String timeString = String.valueOf(sessionTime.getText());
                    editor.putString("Session Time", timeString);
                    editor.apply();

                    if (timeString.isEmpty() || timeString.length() < 4) {
                        Toast.makeText(Profile.this, "Enter a Time in the format: HHMM", Toast.LENGTH_LONG).show();
                    } else {
                        int hours = Integer.valueOf(timeString.substring(0, 2));
                        int minutes = Integer.valueOf(timeString.substring(2, 4));

                        Log.i("Time entered", "Hours: " + String.valueOf(hours) + " Minutes: " + String.valueOf(minutes));
                        Toast.makeText(Profile.this, "Session Time Set Successfully", Toast.LENGTH_LONG).show();

                        //Calendar object formats time to be used in notifications
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        calendar.set(Calendar.SECOND, 0);

                        Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
                        intent.setAction("MY_NOTIFICATION_MESSAGE");

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }else{
                    Toast.makeText(Profile.this, "Allow Notifications First", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Location Permissions
        int locPermission = preferences.getInt("Location Permission", 0);
        locationPerm = findViewById(R.id.locationSwitch);

        //Set the state of the switch
        if (locPermission == 0){
            locationPerm.setChecked(false);
        }else{
            locationPerm.setChecked(true);
        }

        locationPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationPerm.isChecked()) {
                    editor.putInt("Location Permission", 1);

                    //creates a location request
                    locationRequest = locationRequest.create();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(5000);
                    locationRequest.setFastestInterval(2000);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //If build is recent enough
                        if (ActivityCompat.checkSelfPermission(Profile.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){ //If application has location permission

                            if (!isGPSEnabled()){
                                turnOnGPS();
                            }

                        } else {
                            requestPermissions(new String[]{
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            }, 1);
                        }
                    }

                    Toast.makeText(Profile.this, "Location Permission Turned On", Toast.LENGTH_LONG).show();
                } else {
                    editor.putInt("Location Permission", 0);
                    Toast.makeText(Profile.this, "Location Permission Turned Off", Toast.LENGTH_LONG).show();
                }
                editor.apply();
            }
        });

        //Camera permissions
        int camPermission = preferences.getInt("Camera Permission", 0);

        //Set the state of the switch
        cameraPerm = findViewById(R.id.cameraSwitch);
        if (camPermission == 0){
            cameraPerm.setChecked(false);
        }else{
            cameraPerm.setChecked(true);
        }

        cameraPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraPerm.isChecked()) {
                    editor.putInt("Camera Permission", 1);
                    Toast.makeText(Profile.this, "Camera Permission Turned On", Toast.LENGTH_LONG).show();
                } else {
                    editor.putInt("Camera Permission", 0);
                    Toast.makeText(Profile.this, "Camera Permission Turned Off", Toast.LENGTH_LONG).show();
                }
                editor.apply();
            }
        });
    }

    //Turns on device GPS to fetch device location
    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    //Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Profile.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    //Gets whether device GPS is enabled
    //Returns boolean
    private boolean isGPSEnabled(){
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null){
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

}