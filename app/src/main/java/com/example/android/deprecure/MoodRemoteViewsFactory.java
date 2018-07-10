package com.example.android.deprecure;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.android.deprecure.R;
import com.example.android.deprecure.model.Mood;

import java.util.ArrayList;

import static com.example.android.deprecure.MoodTrackWidgetService.ACTION_ADD_MODD;

/**
 * Created by Juraj on 7/8/2018.
 */

public class MoodRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Mood> mMoods;

    public MoodRemoteViewsFactory(Context context, ArrayList<Mood> moods) {
        mContext = context;
        mMoods = moods;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mMoods.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_mood);
        Mood mood = mMoods.get(i);
        remoteViews.setTextViewText(R.id.mood_name, mood.getMoodName());
        remoteViews.setImageViewResource(R.id.mood_smile, mood.getMoodDrawableId());

        Bundle bundle = new Bundle();
        bundle.putInt("WIDGET_MOOD", i);
        Intent intent = new Intent(mContext, MoodTrackWidgetService.class);
        intent.setAction(ACTION_ADD_MODD);
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.mood_smile,  intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
