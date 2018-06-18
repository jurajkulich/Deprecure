package com.example.android.deprecure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.android.deprecure.model.Mood;

import java.util.ArrayList;

import butterknife.BindView;

public class AddItemToDiaryActivity extends AppCompatActivity {

    private ArrayList<Mood> moods = new ArrayList<>();

    @BindView(R.id.add_mood_recyclerview)
    RecyclerView mMoodRecyclerView;

    private MoodAdapter moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_diary);

        moods.add(new Mood("Happy", 0));
        moods.add(new Mood("Smiling", 0));
        moods.add(new Mood("Neutral", 0));
        moods.add(new Mood("Sad", 0));
        moods.add(new Mood("Crying", 0));


        mMoodRecyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (5, StaggeredGridLayoutManager.VERTICAL));

        moodAdapter = new MoodAdapter(moods);
    }
}
