package com.example.android.deprecure;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.deprecure.adapters.ScreenTestSlidePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepressionTestActivity extends FragmentActivity {

    private String questions[] = {
            "Little interest or pleasure in doing things?",
            "Feeling down, depressed, or hopeless?",
            "Trouble falling or staying asleep, or sleeping too much?",
            "Feeling tired or having little energy?",
            "Poor appetite or overeating?",
            "Feeling bad about yourself - or that you are a failure or have let yourself or your family down?",
            "Trouble concentrating on things, such as reading the newspaper or watching television?",
            "Moving or speaking so slowly that other people could have noticed?. " +
                    "Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual?",
            "Thoughts that you would be better off dead, or of hurting yourself in some way?"
    };

    @BindView(R.id.depression_test_viewpager)
    ViewPager mViewPager;

    private ScreenTestSlidePagerAdapter mScreenTestSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression_test);
        ButterKnife.bind(this);

        mScreenTestSlidePagerAdapter = new ScreenTestSlidePagerAdapter(getSupportFragmentManager(), questions);
        mViewPager.setAdapter(mScreenTestSlidePagerAdapter);

    }
}
