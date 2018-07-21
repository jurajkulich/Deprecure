package com.example.android.deprecure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.deprecure.adapters.DiaryEntriesAdapter;
import com.example.android.deprecure.model.DiaryEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryActivity extends AppCompatActivity {

    @BindView(R.id.diary_fab_add_button)
    FloatingActionButton mFab;

    @BindView(R.id.diary_entries_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.diary_toolbar)
    Toolbar mToolbar;

    private LinearLayoutManager mLinearLayoutManager;
    private DiaryEntriesAdapter mDiaryEntriesAdapter;
    private ArrayList<DiaryEntry> mDiaryEntries;

    private int positionIndex;
    private int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDiaryEntries = new ArrayList<>();
        mDiaryEntriesAdapter = new DiaryEntriesAdapter(mDiaryEntries);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mDiaryEntriesAdapter);

        // loading adapter with firebase data
        getData();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemToDiaryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void getData() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase.child("users").child(uid).child("diary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                mDiaryEntries.clear();
                for( DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiaryEntry diaryEntry = snapshot.getValue(DiaryEntry.class);
                    mDiaryEntries.add(diaryEntry);
                    counter++;
                    Log.d("Diary", diaryEntry.getTextEntry());
                }
                mDiaryEntriesAdapter.updateAdapter(mDiaryEntries);
                if( positionIndex != -1) {
                    mLinearLayoutManager.scrollToPositionWithOffset(positionIndex, offset);
                }
                saveCountertoSharedPref(counter);
                EntryCounterWidgetService.startEntryCounterWidgetService(getApplicationContext());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DiaryActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                Log.d("Diary", "Error getting documents: ", databaseError.toException());
            }
        });
    }

    private void saveCountertoSharedPref(int counter) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("COUNTER", counter);
        editor.apply();
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        positionIndex = savedInstanceState.getInt("POSITION");
        offset = savedInstanceState.getInt("OFFSET");
    }

    // https://bit.ly/2JDx3Vq
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        positionIndex = mLinearLayoutManager.findFirstVisibleItemPosition();
        View start = mRecyclerView.getChildAt(0);
        offset = (start == null) ? 0 : (start.getTop() - mRecyclerView.getPaddingTop());
        Log.d("Diary", positionIndex + "");
        outState.putInt("POSITION", positionIndex);
        outState.putInt("OFFSET", offset);
    }
}
