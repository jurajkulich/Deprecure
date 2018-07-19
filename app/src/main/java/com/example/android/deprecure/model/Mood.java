package com.example.android.deprecure.model;

/**
 * Created by juraj on 6/18/18.
 */

public class Mood {

    private String moodName;
    // saving smile drawable id on device
    private int moodDrawableId;

    public Mood(String moodName, int moodDrawableId) {
        this.moodName = moodName;
        this.moodDrawableId = moodDrawableId;
    }

    public Mood() {

    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public int getMoodDrawableId() {
        return moodDrawableId;
    }

    public void setMoodDrawableId(int moodDrawableId) {
        this.moodDrawableId = moodDrawableId;
    }
}
