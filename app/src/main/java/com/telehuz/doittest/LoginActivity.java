package com.telehuz.doittest;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.telehuz.doittest.view.LoginFragment;
import com.telehuz.doittest.view.SignupFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_STORAGE = 0;

    @BindView(R.id.activity_login_button_login)
    Button loginButton;

    @BindView(R.id.activity_login_button_signup)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginButton.setOnClickListener(v -> {
            if (mayRequestContacts()) {
                openFragment(new LoginFragment());
            }
        });
        signupButton.setOnClickListener(v -> {
            if (mayRequestContacts()) {
                openFragment(new SignupFragment());
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {

            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar.make(rootView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                        }
                    })
                    .show();
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
        return false;
    }
}
