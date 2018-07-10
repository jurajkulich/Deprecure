package com.example.android.deprecure;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Juraj on 7/8/2018.
 */

public class MoodTrackWidgetService extends IntentService {

    public static final String ACTION_ADD_MODD = "com.example.android.deprecure.action.add_mood";

    public MoodTrackWidgetService() {
        super("com.example.android.deprecure.MoodTrackWidgetService");
    }

    public static void startMoodTrackService(Context context) {
        Intent intent = new Intent(context, MoodTrackWidgetService.class);
        intent.setAction(ACTION_ADD_MODD);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if( action.equals(ACTION_ADD_MODD)) {
                handleAddMoodService();
            }
        }
    }

    private void handleAddMoodService() {
//        Toast.makeText(this, "Clicked ", Toast.LENGTH_SHORT).show();
        Log.d("SERVICE", "CLICKED");
    }
}
