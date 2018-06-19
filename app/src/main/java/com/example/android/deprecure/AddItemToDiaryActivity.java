package com.example.android.deprecure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.android.deprecure.model.Mood;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemToDiaryActivity extends AppCompatActivity {

    private ArrayList<Mood> moods = new ArrayList<>();

    @BindView(R.id.add_mood_recyclerview)
    RecyclerView mMoodRecyclerView;

    private MoodAdapter moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_diary);
        ButterKnife.bind(this);

        moods.add(new Mood("Happy",
                getResources().getIdentifier("happy1", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Smiling",
                getResources().getIdentifier("happy", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Neutral",
                getResources().getIdentifier("sceptic", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Sad",
                getResources().getIdentifier("sad", "drawable", "com.example.android.deprecure")));
        moods.add(new Mood("Crying",
                getResources().getIdentifier("crying", "drawable", "com.example.android.deprecure")));

        mMoodRecyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (4, StaggeredGridLayoutManager.VERTICAL));
        moodAdapter = new MoodAdapter(moods);
        mMoodRecyclerView.setAdapter(moodAdapter);
    }
}
