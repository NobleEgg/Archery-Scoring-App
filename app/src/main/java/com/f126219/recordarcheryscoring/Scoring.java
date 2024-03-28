package com.f126219.recordarcheryscoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scoring extends AppCompatActivity {

    View topDisplay, buttonContainer, roundCompleteModal;
    Button close;
    ImageView ten, nine, eight, seven, six, five, four, three, two, one, miss;
    TextView roundName;
    ArrayList<Integer> scores = new ArrayList<Integer>();
    ArrayList<Integer> runningScores = new ArrayList<Integer>();
    ArrayList<View> dozenFrames = new ArrayList<View>();
    int endTotal;

    //OVERVIEW: Logic for the activity used to score a round of archery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Bring navbar and button container to front
        topDisplay = findViewById(R.id.topDisplay);
        buttonContainer = findViewById(R.id.buttonContainer);
        topDisplay.bringToFront();
        buttonContainer.bringToFront();

        //Button to close scoring activity and cancel the round
        close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), roundInformation.class);
                startActivity(intent);
                finish();
            }
        });

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences(
                getString(R.string.shared_preferences_key), MODE_PRIVATE);
        roundName = findViewById(R.id.roundNameText);
        roundName.setText(preferences.getString("currentScoringRound", "Archery Round")); //get round name to display on page

        //Find the views required to complete scoring
        dozenFrames.add(findViewById(R.id.firstDozen));
        dozenFrames.add(findViewById(R.id.secondDozen));
        dozenFrames.add(findViewById(R.id.thirdDozen));
        dozenFrames.add(findViewById(R.id.fourthDozen));
        dozenFrames.add(findViewById(R.id.fifthDozen));
        View currentFrame = dozenFrames.get(0); //start on the top frame

        ten = findViewById(R.id.tenButton);
        nine = findViewById(R.id.nineButton);
        eight = findViewById(R.id.eightButton);
        seven = findViewById(R.id.sevenButton);
        six = findViewById(R.id.sixButton);
        five = findViewById(R.id.fiveButton);
        four = findViewById(R.id.fourButton);
        three = findViewById(R.id.threeButton);
        two = findViewById(R.id.twoButton);
        one = findViewById(R.id.oneButton);
        miss = findViewById(R.id.missButton);

        //Set all score buttons to add score to running result and display score to screen
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(10);
                runningScores.add(10);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame); //determines where 10 should be displayed

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.ten));
                currentFrameIcon.setVisibility(View.VISIBLE); //Changes image view to a 10 and sets to visible

                //Updates displayed scores
                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores); //Is the round finished
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(9);
                runningScores.add(9);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.nine));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(8);
                runningScores.add(8);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.eight));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(7);
                runningScores.add(7);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.seven));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(6);
                runningScores.add(6);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.six));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(5);
                runningScores.add(5);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.five));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(4);
                runningScores.add(4);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.four));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(3);
                runningScores.add(3);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.three));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(2);
                runningScores.add(2);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.two));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(1);
                runningScores.add(1);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.one));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
        miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scores.add(0);
                runningScores.add(0);
                //change on screen display
                View currentFrame = dozenFrames.get(getCurrentDozen(scores));
                ImageView currentFrameIcon = getFrame(scores, currentFrame);

                currentFrameIcon.setImageDrawable(getDrawable(R.drawable.miss));
                currentFrameIcon.setVisibility(View.VISIBLE);

                TextView currentTotal = getCurrentEnd(scores, currentFrame);
                endTotal = calculateResult(runningScores);
                currentTotal.setText(Integer.toString(endTotal));
                runningScores = resetRunningScore(runningScores);

                roundFinished(scores);
            }
        });
    }

    //Calculates the total score
    //Takes an array list of integers | returns the sum of the integers
    public int calculateResult(ArrayList<Integer> scores){
       int total = 0;
       for (int i=0; i < scores.size(); i++){
           total += scores.get(i);
       }
       return total;
    }

    //Gets the current row of the score sheet to update the running total
    //Takes an array list of integers and the current frame of the score sheet | returns the textview within the frame to be updated
    public TextView getCurrentEnd(ArrayList<Integer> scores, View currentFrame){
        int currentArrow = (scores.size() % 12);
        if (currentArrow <= 6 && currentArrow != 0){
            return currentFrame.findViewById(R.id.halfDozenTotal);
        }else{
            return currentFrame.findViewById(R.id.halfDozenTotal2);
        }
    };

    //Clears the running total
    //Takes an array list of integers | returns an empty array list of integers
    public ArrayList<Integer> resetRunningScore(ArrayList<Integer> runningScores){
        if (runningScores.size() == 6){
            runningScores.clear();
        }
        return runningScores;
    }

    //Gets the current square within the score sheet to be updated
    //Takes an array list of integers and the current frame of the score sheet | returns the current square within the score sheet
    public ImageView getFrame(ArrayList<Integer> scores, View currentFrame){
        int framePosition = scores.size() % 12;
        switch(framePosition){
            case 1:
                return currentFrame.findViewById(R.id.col1row1);
            case 2:
                return currentFrame.findViewById(R.id.col1row2);
            case 3:
                return currentFrame.findViewById(R.id.col1row3);
            case 4:
                return currentFrame.findViewById(R.id.col1row4);
            case 5:
                return currentFrame.findViewById(R.id.col1row5);
            case 6:
                return currentFrame.findViewById(R.id.col1row6);
            case 7:
                return currentFrame.findViewById(R.id.col2row1);
            case 8:
                return currentFrame.findViewById(R.id.col2row2);
            case 9:
                return currentFrame.findViewById(R.id.col2row3);
            case 10:
                return currentFrame.findViewById(R.id.col2row4);
            case 11:
                return currentFrame.findViewById(R.id.col2row5);
            case 0:
                return currentFrame.findViewById(R.id.col2row6);
        }
        return  currentFrame.findViewById(R.id.col1row1);
    }

    //Gets the index of the current frame of the score sheet
    //Takes an array list of integers | returns the index of the current frame
    public int getCurrentDozen(ArrayList<Integer> scores){
        if (scores.size() <= 12){
            return 0;
        }else if (scores.size() <= 24){
            return 1;
        }else if (scores.size() <= 36){
            return 2;
        }else if (scores.size() <= 48){
            return 3;
        }else{
            return 4;
        }

    }

    //Adds the completed round to an SQL database and displays a modal
    //Takes an array list of integers
    public void roundFinished(ArrayList<Integer> scores){
        if (scores.size() >= 60){
            //Round over
            int result = calculateResult(scores);


            //Shared Preferences
            SharedPreferences preferences = getSharedPreferences(
                    getString(R.string.shared_preferences_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            //Get round information
            String roundName = preferences.getString("currentScoringRound", "Archery Round");
            String location = preferences.getString("currentScoringLocation", "Location");
            String date = preferences.getString("currentScoringDate", "Date");
            String bowStyle = preferences.getString("currentScoringBowStyle", "Bow Style");
            boolean record;

            String username = preferences.getString("currentUserUsername", "Username");
            int currentRoundRecord = preferences.getInt(username + " " + roundName + " Record", 0);
            if (result > currentRoundRecord){
                record = true;
                editor.putInt(username + " " + roundName + " Record", result);
            }else{
                record = false;
            }

            //Write score to SQL database
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            long error = databaseHelper.writeResults(roundName, bowStyle, location, date, result, record);
             if (error == -1){
                 Toast.makeText(this, "An Error Occurred", Toast.LENGTH_LONG).show();
             }
             databaseHelper.close();

             //Displays a modal with a summary of the round
            roundCompleteModal = findViewById(R.id.roundCompleteModal);

             TextView roundSummary = roundCompleteModal.findViewById(R.id.summaryText);
             TextView roundScore = roundCompleteModal.findViewById(R.id.result);
             Button takePhoto, endRound;

             roundSummary.setText("You completed a "+ roundName + " using a " + bowStyle + " bow and scored:");
             roundScore.setText(Integer.toString(result));

             //Allows the user to open the camera
             takePhoto = roundCompleteModal.findViewById(R.id.photoButton);
             takePhoto.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (preferences.getInt("Camera Permission", 0) == 1){ //If camera permission is enabled

                         //Intent to camera app
                         final int REQUEST_IMAGE_CAPTURE = 1;
                         Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                         try {
                             startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE);
                         } catch (ActivityNotFoundException e) {
                             Toast.makeText(Scoring.this, "An Error Occurred", Toast.LENGTH_LONG).show();
                         }

                     } else {
                         Toast.makeText(Scoring.this, "Camera Permissions Not Enabled", Toast.LENGTH_LONG).show();
                     }
                 }
             });

             //Button to end scoring and return to homepage
             endRound = roundCompleteModal.findViewById(R.id.completeButton);
             endRound.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(getApplicationContext(), Homepage.class);
                     startActivity(intent);
                     finish();
                 }
             });

             ImageView[] buttons = {miss, one, two, three, four, five, six, seven, eight, nine, ten};
             for (int i=0; i<buttons.length; i++){
                 buttons[i].setClickable(false);
             }

             //Sets the modal to visible
            roundCompleteModal.setVisibility(View.VISIBLE);
        }
    }
}