package com.example.android.deprecure;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenTestSlideFragment extends Fragment {

    private String question;

    @BindView(R.id.test_slide_question)
    TextView mQuestionTextView;

    public ScreenTestSlideFragment() {
        // Required empty public constructor
    }

    public static ScreenTestSlideFragment getInstance(String question) {
        ScreenTestSlideFragment slideFragment = new ScreenTestSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        slideFragment.setArguments(bundle);
        return slideFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_screen_test_slide, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if( bundle != null) {
            question = bundle.getString("QUESTION");
            mQuestionTextView.setText(question);
        }

        return rootView;
    }

}
