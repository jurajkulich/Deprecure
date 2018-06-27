package com.example.android.deprecure;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.deprecure.adapters.ActivityAdapter;
import com.example.android.deprecure.adapters.MoodAdapter;
import com.example.android.deprecure.model.DiaryEntry;
import com.example.android.deprecure.model.Mood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemToDiaryActivity extends AppCompatActivity implements MoodAdapter.OnClickListener, ActivityAdapter.OnClickListener{

    private ArrayList<Mood> moods = new ArrayList<>();
    private ArrayList<String> activities = new ArrayList<>();

    private Mood entryMood;
    private ArrayList<String> entryActivities;
    private String entryText;

    @BindView(R.id.add_mood_recyclerview)
    RecyclerView mMoodRecyclerView;

    @BindView(R.id.add_activity_recyclerview)
    RecyclerView mActivityRecyclerView;

    @BindView(R.id.add_text_edittext)
    EditText mTextEditText;

    @BindView(R.id.add_item_to_diary_fab)
    FloatingActionButton mFloatingActionButton;

    private MoodAdapter moodAdapter;
    private ActivityAdapter mActivityAdapter;

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

        activities.add("Running");
        activities.add("Working");
        activities.add("Sleeping");
        activities.add("Learning");
        activities.add("Crying");
        activities.add("Cycling");
        activities.add("Playing football");

        mMoodRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActivityRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                }
        );
        moodAdapter = new MoodAdapter(moods, this);
        mMoodRecyclerView.setAdapter(moodAdapter);

        mActivityAdapter = new ActivityAdapter(activities, this);
        mActivityRecyclerView.setAdapter(mActivityAdapter);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddItemToDiaryActivity.this, "Added", Toast.LENGTH_SHORT).show();
                entryText = mTextEditText.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                DiaryEntry diaryEntry = new DiaryEntry(entryText, entryActivities, entryMood);
                database.collection("users").document(uid).collection("diary").document(entryText).set(diaryEntry);
            }
        });

    }

    @Override
    public void onItemClick(Mood mood) {
        Toast.makeText(this, mood.getMoodName(), Toast.LENGTH_SHORT).show();
        entryMood = mood;
    }

    @Override
    public void onItemClick(String activity) {
        Toast.makeText(this, activity, Toast.LENGTH_SHORT).show();
        if( entryActivities == null) {
            entryActivities = new ArrayList<>();
        }
        entryActivities.add(activity);
    }


}
