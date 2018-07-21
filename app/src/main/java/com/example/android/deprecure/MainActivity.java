package com.example.android.deprecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 199;

    private FirebaseAuth auth;

    @BindView(R.id.main_textview_sign_in)
    TextView mLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        auth = FirebaseAuth.getInstance();
        // when is user logged in we'll open menu
        if( auth.getCurrentUser() != null) {
            Intent intent = new Intent(this, PrimaryMenuActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // opening FirebaseUI on click
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInScreen();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == RC_SIGN_IN) {
            if( resultCode == RESULT_OK) {
                showSignInScreen();
            }
        }
    }

    public void showSignInScreen() {
        if( auth.getCurrentUser() == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
                    setAvailableProviders(providers).
                    setTheme(R.style.FirebaseUI_Theme)
                    .build(), RC_SIGN_IN);
        } else {
            Intent intent = new Intent(this, PrimaryMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
