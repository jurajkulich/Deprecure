package com.example.android.deprecure;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.android.deprecure.model.Mood;

import java.util.ArrayList;

/**
 * Created by Juraj on 7/8/2018.
 */

public class MoodRemoteViewsService extends RemoteViewsService {

    private ArrayList<Mood> moods= new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        moods.add(new Mood("Happy",
                getResources().getIdentifier("happy1", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Smiling",
                getResources().getIdentifier("happy", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Neutral",
                getResources().getIdentifier("sceptic", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Sad",
                getResources().getIdentifier("sad", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Angry",
                getResources().getIdentifier("angry", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Crying",
                getResources().getIdentifier("crying", "drawable", "com.example.android.deprecure")));
        return new MoodRemoteViewsFactory(this, moods);
    }
}
