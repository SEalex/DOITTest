package com.telehuz.doittest.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.telehuz.doittest.R;
import com.telehuz.doittest.presenter.StartPresenter;
import com.telehuz.doittest.view.StartView;
import com.telehuz.doittest.view.fragment.LoginFragment;
import com.telehuz.doittest.view.fragment.SignupFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements StartView {

    @BindView(R.id.activity_login_button_login)
    Button loginButton;

    @BindView(R.id.activity_login_button_signup)
    Button signupButton;

    StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        presenter = new StartPresenter(this);

        loginButton.setOnClickListener(v -> {
            presenter.onLoginButtonClick();
        });

        signupButton.setOnClickListener(v -> {
            presenter.onSignupButtonClick();
        });
    }

    @Override
    public void openLoginFragment() {
        openFragment(new LoginFragment());
    }

    @Override
    public void openSignupFragment() {
        openFragment(new SignupFragment());
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
