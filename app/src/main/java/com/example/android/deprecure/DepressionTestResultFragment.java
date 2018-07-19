package com.example.android.deprecure;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class DepressionTestResultFragment extends Fragment {

    private int mResult;

    @BindView(R.id.fragment_depression_test_result)
    TextView mResultTextView;

    @BindView(R.id.fragment_depression_test_result_comment)
    TextView mResultComment;

    public DepressionTestResultFragment() {
        // Required empty public constructor
    }

    public static DepressionTestResultFragment getInstance(int result) {
        DepressionTestResultFragment depressionTestResultFragment = new DepressionTestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("RESULT", result);
        depressionTestResultFragment.setArguments(bundle);
        return depressionTestResultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if( bundle != null) {
            mResult = bundle.getInt("RESULT");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_depression_test_result, container, false);
        ButterKnife.bind(this, rootView);

        if( mResult <= 9 ) {
            mResultComment.setText(R.string.depression_weak_comment);
        } else {
            mResultComment.setText(getString(R.string.depression_strong_comment));
        }

        if( mResult <= 4) {
            mResultTextView.setText(R.string.none_depression);
            mResultComment.setText(R.string.none_depression_text);
        } else if( mResult <= 9) {
            mResultTextView.setText(R.string.mild_depression);
        } else if( mResult <= 14) {
            mResultTextView.setText(R.string.moderate_depression);
        } else if( mResult <= 19) {
            mResultTextView.setText(R.string.moderately_severe_depression);
        } else {
            mResultTextView.setText(R.string.severe_depression);
        }

        return rootView;
    }

}
