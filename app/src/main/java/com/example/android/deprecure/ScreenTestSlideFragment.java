package com.example.android.deprecure;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
public class ScreenTestSlideFragment extends Fragment implements View.OnClickListener {

    public interface OnAnswerClick{
        void clickedAnswer(int answer, int position);
    }

    OnAnswerClick mOnAnswerClick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnAnswerClick = (OnAnswerClick) context;
    }

    private String question;
    private int position;

    @BindView(R.id.test_slide_question)
    TextView mQuestionTextView;

    @BindView(R.id.depression_test_fab)
    FloatingActionButton mFloatingActionButton;

    public ScreenTestSlideFragment() {
        // Required empty public constructor
    }

    public static ScreenTestSlideFragment getInstance(String question, int position) {
        ScreenTestSlideFragment slideFragment = new ScreenTestSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        bundle.putInt("POSITION", position);
        slideFragment.setArguments(bundle);
        return slideFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_screen_test_slide, container, false);
        ButterKnife.bind(this, rootView);

        // each widget is loaded with different question
        Bundle bundle = this.getArguments();
        if( bundle != null) {
            question = bundle.getString("QUESTION");
            position = bundle.getInt("POSITION");
            mQuestionTextView.setText(question);
        }
        if( position == 8) {
            mFloatingActionButton.setVisibility(View.VISIBLE);
        }
        rootView.findViewById(R.id.radio_not_at_all).setOnClickListener(this);
        rootView.findViewById(R.id.radio_several_days).setOnClickListener(this);
        rootView.findViewById(R.id.radio_more_than_half).setOnClickListener(this);
        rootView.findViewById(R.id.radio_nearly_every_day).setOnClickListener(this);

        rootView.findViewById(R.id.depression_test_fab).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.radio_not_at_all: {
                mOnAnswerClick.clickedAnswer(0, position);
                break;
            }
            case R.id.radio_several_days: {
                mOnAnswerClick.clickedAnswer(1, position);
                break;
            }
            case R.id.radio_more_than_half: {
                mOnAnswerClick.clickedAnswer(2, position);
                break;
            }
            case R.id.radio_nearly_every_day: {
                mOnAnswerClick.clickedAnswer(3, position);
                break;
            }
            case R.id.depression_test_fab: {
                mOnAnswerClick.clickedAnswer(-1, -1);
                break;
            }
            default:
                mOnAnswerClick.clickedAnswer(0, position);
        }

    }
}
