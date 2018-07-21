package com.example.android.deprecure;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by Juraj on 7/21/2018.
 */

public class EntryCounterWidgetService extends IntentService {

    public static final String ACTION_COUNTER = "android.deprecure.action.counter";

    public EntryCounterWidgetService() {
        super("com.example.android.deprecure.EntryCounterWidgetService");
    }

    public static  void startEntryCounterWidgetService(Context context) {
        Intent intent = new Intent(context, EntryCounterWidgetService.class);
        intent.setAction(ACTION_COUNTER);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if( action.equals(ACTION_COUNTER)) {
                handleWidgetCounter();
            }
        }
    }

    private void handleWidgetCounter() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, EntryCounterWidget.class));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int counter = preferences.getInt("COUNTER", 0);
        //Now update all widgets
        EntryCounterWidget.updateCounterWidget(this, appWidgetManager, appWidgetIds, counter);
    }
}
