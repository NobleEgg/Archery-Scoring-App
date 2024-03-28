package com.f126219.recordarcheryscoring;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    ArrayList<roundSummaryModel> roundSummaryModels = new ArrayList<>();

    View homepage;
    TextView noRecordsHeading, noRecordsSubheading;
    ImageView profileImage, targetImage, trophyImage;
    Button scoreButton, profileButton;
    View navbar;


    //Overview: Main activity of the application, displays all records as a recyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Bring navbar to front
        navbar = findViewById(R.id.navbar);
        navbar.bringToFront();

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

        //Responding to swipe touch events
        homepage = findViewById(R.id.homepage);
        homepage.setOnTouchListener(new OnSwipeTouchListener(Homepage.this) {
             public void onSwipeLeft() {
                 Intent intent = new Intent(getApplicationContext(), roundInformation.class);
                 startActivity(intent);
                 finish();
             }
         });


        //Read all records from database
        ArrayList<String[]> userRecords = new ArrayList<String[]>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userRecords = databaseHelper.getAllScores();

        if (userRecords.size() > 0){ //if records returned

            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            //Initialise a class for each record
            for (int i=0; i<userRecords.size(); i++){
                roundSummaryModels.add(new roundSummaryModel(
                        userRecords.get(i)[0],
                        userRecords.get(i)[1],
                        userRecords.get(i)[2],
                        userRecords.get(i)[3],
                        userRecords.get(i)[4],
                        userRecords.get(i)[5])
                );
            }

            //Hide default text
            noRecordsHeading = findViewById(R.id.noRecordsText);
            noRecordsSubheading = findViewById(R.id.noRecordsSubtext);
            noRecordsHeading.setVisibility(View.INVISIBLE);
            noRecordsSubheading.setVisibility(View.INVISIBLE);

            //Create recyclerView
            recordsRecyclerViewAdapter adapter = new recordsRecyclerViewAdapter(this, roundSummaryModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }


}