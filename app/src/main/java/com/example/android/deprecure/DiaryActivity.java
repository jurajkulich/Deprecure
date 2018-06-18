package com.example.android.deprecure;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryActivity extends AppCompatActivity {

    @BindView(R.id.diary_fab_add_button)
    FloatingActionButton mFab;

    private android.support.v4.app.FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Fragment fragment = new AddItemToDiaryFragment();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.diary_fragment_container, fragment).commit();
                */
                Intent intent = new Intent(getApplicationContext(), AddItemToDiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
