package com.example.android.deprecure;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepressionTestResultFragment extends Fragment {

    private int mResult;

    private TextView mResultTextView;

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

        mResultTextView = rootView.findViewById(R.id.fragment_depression_test_result);


        if( mResult <= 4) {
            mResultTextView.setText("None depression");
        } else if( mResult <= 9) {
            mResultTextView.setText("Mild depression");
        } else if( mResult <= 14) {
            mResultTextView.setText("Moderate depression");
        } else if( mResult <= 19) {
            mResultTextView.setText("Moderately severe depression");
        } else {
            mResultTextView.setText("Severe depression");
        }

        return rootView;
    }

}
