package com.example.android.deprecure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.android.deprecure.model.DiaryEntry;
import com.example.android.deprecure.widgets.EntryCounterWidgetService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Juraj on 7/21/2018.
 */

public class DatabaseHelper {

    private static DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    private static String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static void addDataEntry(DiaryEntry diaryEntry) {
        firebaseDatabase.child("users").child(DatabaseHelper.getUid()).child("diary").push().setValue(diaryEntry);
    }

    public static DatabaseReference getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public static void setFirebaseDatabase(DatabaseReference firebaseDatabase) {
        DatabaseHelper.firebaseDatabase = firebaseDatabase;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        DatabaseHelper.uid = uid;
    }
}
