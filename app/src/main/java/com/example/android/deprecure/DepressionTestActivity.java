package com.example.android.deprecure;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.deprecure.adapters.ScreenTestSlidePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepressionTestActivity extends FragmentActivity implements ScreenTestSlideFragment.OnAnswerClick {

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

    private int[] answers =  new int[9];

    @BindView(R.id.depression_test_viewpager)
    ViewPager mViewPager;

    private ScreenTestSlidePagerAdapter mScreenTestSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression_test);
        ButterKnife.bind(this);

        for( int i = 0; i < 9; i++) {
            answers[i] = 0;
        }

        mScreenTestSlidePagerAdapter = new ScreenTestSlidePagerAdapter(getSupportFragmentManager(), questions);
        mViewPager.setAdapter(mScreenTestSlidePagerAdapter);

    }

    @Override
    public void clickedAnswer(int answer, int position) {
        if( answer == -1) {
            int points = answerResult();
            if( points <= 4) {
                Toast.makeText(this, "None depression", Toast.LENGTH_SHORT).show();
            } else if( points <= 9) {
                Toast.makeText(this, "Mild depression", Toast.LENGTH_SHORT).show();
            } else if( points <= 14) {
                Toast.makeText(this, "Moderate depression", Toast.LENGTH_SHORT).show();
            } else if( points <= 19) {
                Toast.makeText(this, "Moderately severe depression", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Severe depression", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        Toast.makeText(this, position + ": " + answer, Toast.LENGTH_SHORT).show();
        answers[position] = answer;
    }

    private int answerResult() {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            sum += answers[i];
        }
        return sum;
    }


}
