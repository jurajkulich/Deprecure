package com.example.android.deprecure;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.android.deprecure.model.DiaryEntry;
import com.example.android.deprecure.model.Mood;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryActivity extends AppCompatActivity implements DiaryEntriesAdapter.OnClickListener{

    @BindView(R.id.diary_fab_add_button)
    FloatingActionButton mFab;

    @BindView(R.id.diary_entries_recyclerview)
    RecyclerView mRecyclerView;

    private DiaryEntriesAdapter mDiaryEntriesAdapter;

    private ArrayList<DiaryEntry> mDiaryEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);

        mDiaryEntries = new ArrayList<>();
        mDiaryEntriesAdapter = new DiaryEntriesAdapter(mDiaryEntries, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDiaryEntriesAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid).collection("diary").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if( task.isSuccessful()) {
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                DiaryEntry entry = documentSnapshot.toObject(DiaryEntry.class);
                                mDiaryEntries.add(entry);
                                Log.d("Diary", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("Diary", entry.getTextEntry() + entry.getActivityEntries() + entry.getMood().getMoodName());
                                mDiaryEntriesAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d("Diary", "Error getting documents: ", task.getException());
                        }
                    }
                }
        );

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemToDiaryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(String activity) {

    }
}
