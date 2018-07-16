package com.example.android.deprecure.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juraj on 6/13/2018.
 */

public class DiaryEntry {

    private String textEntry;
    private ArrayList<String> activityEntries;
    private Mood mMood;

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @ServerTimestamp
    private Date entryDate;

    public DiaryEntry(String textEntry, ArrayList<String> activityEntries, Mood mood) {
        this.textEntry = textEntry;
        this.activityEntries = activityEntries;
        mMood = mood;
    }

    public DiaryEntry() {

    }

    public String getTextEntry() {
        return textEntry;
    }

    public void setTextEntry(String textEntry) {
        this.textEntry = textEntry;
    }

    public ArrayList<String> getActivityEntries() {
        return activityEntries;
    }

    public void setActivityEntries(ArrayList<String> activityEntries) {
        this.activityEntries = activityEntries;
    }

    public Mood getMood() {
        return mMood;
    }

    public void setMood(Mood mood) {
        mMood = mood;
    }
}
