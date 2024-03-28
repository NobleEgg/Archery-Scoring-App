package com.f126219.recordarcheryscoring;

//OVERVIEW: Class used to display records on homepage
public class roundSummaryModel {
    String round, bowStyle, location, date, score, recordStatus;


    public roundSummaryModel(String round, String bowStyle, String location, String date, String score, String recordStatus) {
        this.round = round;
        this.bowStyle = bowStyle;
        this.location = location;
        this.date = date;
        this.score = score;
        this.recordStatus = recordStatus;
    }

    public String getRound() {
        return round;
    }

    public String getBowStyle() {
        return bowStyle;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }

    public String getRecordStatus() {
        return recordStatus;
    }
}
