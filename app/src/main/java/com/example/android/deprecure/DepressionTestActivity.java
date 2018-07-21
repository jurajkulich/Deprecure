package com.example.android.deprecure;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.android.deprecure.adapters.ScreenTestSlidePagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepressionTestActivity extends FragmentActivity implements ScreenTestSlideFragment.OnAnswerClick {

    private int[] answers = new int[9];

    @BindView(R.id.depression_test_viewpager)
    ViewPager mViewPager;

    private ScreenTestSlidePagerAdapter mScreenTestSlidePagerAdapter;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression_test);
        ButterKnife.bind(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        for( int i = 0; i < 9; i++) {
            answers[i] = 0;
        }

        mScreenTestSlidePagerAdapter = new ScreenTestSlidePagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.test_questions));
        mViewPager.setAdapter(mScreenTestSlidePagerAdapter);

    }

    @Override
    public void clickedAnswer(int answer, int position) {
        // clicked FAB for test complete
        if( answer == -1) {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            DepressionTestResultFragment fragment
                    = DepressionTestResultFragment.getInstance(answerResult());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    add(R.id.depression_test_result_placeholder, fragment).commit();
            findViewById(R.id.depression_test_result_placeholder).setVisibility(View.VISIBLE);
            return;
        }
        answers[position] = answer;
        if( position < 8) {
            mViewPager.setCurrentItem(++position);
        }
    }

    private int answerResult() {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            sum += answers[i];
        }
        return sum;
    }


}
