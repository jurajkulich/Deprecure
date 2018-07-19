package com.example.android.deprecure;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.deprecure.model.DiaryEntry;
import com.example.android.deprecure.model.Mood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


/**
 * Created by Juraj on 7/8/2018.
 */

public class    MoodTrackWidgetService extends IntentService {

    public static final String ACTION_ADD_MODD = "android.deprecure.action.add_mood";

    public MoodTrackWidgetService() {
        super("com.example.android.deprecure.MoodTrackWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if( action.equals(ACTION_ADD_MODD)) {
                Mood mood = new Mood(intent.getStringExtra("WIDGET_MOOD"),
                        intent.getIntExtra("WIDGET_MOOD_SMILE", 0));
                handleAddMoodService(mood);
            }
        }
    }

    private void handleAddMoodService(Mood mood) {
        // when is no one logged in, widget click is ignored
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }
        // adding mood as diaryEntry to diary in background
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DiaryEntry diaryEntry = new DiaryEntry("", null, mood, Calendar.getInstance().getTime());
        database.collection("users").document(uid)
                .collection("diary").document().set(diaryEntry);
        Log.d("SERVICE", "Added");
    }
}
