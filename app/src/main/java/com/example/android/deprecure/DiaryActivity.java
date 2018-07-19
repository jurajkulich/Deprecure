package com.example.android.deprecure;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.deprecure.adapters.DiaryEntriesAdapter;
import com.example.android.deprecure.model.DiaryEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid).collection("diary")
                .orderBy("entryDate", Query.Direction.DESCENDING).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mDiaryEntries.clear();
                        if( task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                DiaryEntry entry = documentSnapshot.toObject(DiaryEntry.class);
                                mDiaryEntries.add(entry);
                            }
                            mDiaryEntriesAdapter.updateAdapter(mDiaryEntries);
                            if( positionIndex != -1) {
                                mLinearLayoutManager.scrollToPositionWithOffset(positionIndex, offset);
                            }
                        } else {
                            Toast.makeText(DiaryActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                            Log.d("Diary", "Error getting documents: ", task.getException());
                        }
                    }
                }

        );
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == 0) {
            if( resultCode == RESULT_OK) {
                getData();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        positionIndex = savedInstanceState.getInt("POSITION");
        offset = savedInstanceState.getInt("OFFSET");
    }

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
