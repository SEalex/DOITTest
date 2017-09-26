package com.telehuz.doittest.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telehuz.doittest.R;
import com.telehuz.doittest.presenter.LoginPresenter;
import com.telehuz.doittest.view.LoginView;
import com.telehuz.doittest.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements LoginView {

    @BindView(R.id.fragment_login_button_login)
    Button loginButton;

    @BindView(R.id.fragment_login_edit_email)
    EditText editEmail;

    @BindView(R.id.fragment_login_edit_password)
    EditText editPassword;

    @BindView(R.id.fragment_login_progress)
    ProgressBar progressBar;

    LoginPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        presenter = new LoginPresenter(this);

        loginButton.setOnClickListener(v -> presenter.onLoginButtonClick(editEmail.getText().toString(), editPassword.getText().toString()));

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    @Override
    public void resetErrors() {

        editEmail.setError(null);
        editPassword.setError(null);
    }

    @Override
    public void showEmailError(int error) {

        editEmail.setError(getString(error));
        editEmail.requestFocus();
    }

    @Override
    public void showPasswordError(int error) {

        editPassword.setError(getString(error));
        editPassword.requestFocus();
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}