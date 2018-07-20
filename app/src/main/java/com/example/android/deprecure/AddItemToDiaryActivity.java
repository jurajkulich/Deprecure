package com.example.android.deprecure;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.deprecure.adapters.ActivityAdapter;
import com.example.android.deprecure.adapters.MoodAdapter;
import com.example.android.deprecure.model.DiaryEntry;
import com.example.android.deprecure.model.Mood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemToDiaryActivity extends AppCompatActivity implements MoodAdapter.OnClickListener, ActivityAdapter.OnClickListener{

    private ArrayList<Mood> moods = new ArrayList<>();
    private ArrayList<String> activities = new ArrayList<>();

    private Mood entryMood;
    private HashMap<Integer, String> entryActivities;
    private String entryText;

    @BindView(R.id.add_mood_recyclerview)
    RecyclerView mMoodRecyclerView;

    @BindView(R.id.add_activity_recyclerview)
    RecyclerView mActivityRecyclerView;

    @BindView(R.id.add_text_edittext)
    EditText mTextEditText;

    @BindView(R.id.add_item_to_diary_fab)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.add_item_toolbar)
    android.support.v7.widget.Toolbar mToolbar;

    private MoodAdapter moodAdapter;
    private ActivityAdapter mActivityAdapter;

    private int savedMood;
    private String savedText;
    private ArrayList<Integer> savedActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_diary);
        ButterKnife.bind(this);

        // saving instance of selected mood, activities and text
        savedMood = -1;
        savedText = "";
        if( savedInstanceState != null) {
            savedMood = savedInstanceState.getInt("MOOD");
            savedText = savedInstanceState.getString("TEXT");
            savedActivities = savedInstanceState.getIntegerArrayList("ACTIVITIES");
        }

        if( entryActivities == null) {
            entryActivities = new HashMap<>();
        }

        mTextEditText.setText(savedText);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        activities.add("playing sports");
        activities.add("working");
        activities.add("sleeping");
        activities.add("learning");
        activities.add("crying");
        activities.add("reading");
        activities.add("watching TV");
        activities.add("listening to music");

        mMoodRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActivityRecyclerView.setLayoutManager(
                new GridLayoutManager(this, 4) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                }
        );
        moodAdapter = new MoodAdapter(moods, this, savedMood);
        mMoodRecyclerView.setAdapter(moodAdapter);

        mActivityAdapter = new ActivityAdapter(activities, this, savedActivities);
        mActivityRecyclerView.setAdapter(mActivityAdapter);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we'll decide if we update adapter by result
                // user have to select mood
                if( entryMood == null ) {
                    Toast.makeText(AddItemToDiaryActivity.this, "You have to choose the mood!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Date currentDate = Calendar.getInstance().getTime();
                entryText = mTextEditText.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
                DiaryEntry diaryEntry = new DiaryEntry(entryText, new ArrayList<String>(entryActivities.values()), entryMood, currentDate);
                firebaseDatabase.child("users").child(uid).child("diary").push().setValue(diaryEntry);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(Mood mood) {
        entryMood = mood;
    }

    @Override
    public void onItemClick(String activity, int position) {
        // we are saving clicked activities so we can deselect them when clicked again
        if( entryActivities.get(position) != null) {
            entryActivities.remove(position);
        } else {
            entryActivities.put(position, activity);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("MOOD", moods.indexOf(entryMood));
        outState.putString("TEXT", mTextEditText.getText().toString());
        outState.putIntegerArrayList("ACTIVITIES", new ArrayList<Integer>(entryActivities.keySet()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
