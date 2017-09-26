package com.telehuz.doittest.presenter;

import android.text.TextUtils;

import com.telehuz.doittest.R;
import com.telehuz.doittest.model.Model;
import com.telehuz.doittest.model.ModelImpl;
import com.telehuz.doittest.model.TokenStorage;
import com.telehuz.doittest.model.data.AuthResponse;
import com.telehuz.doittest.view.LoginView;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class LoginPresenter {

    private Model model = new ModelImpl();
    private LoginView loginView;

    private Subscription subscription = Subscriptions.empty();

    private boolean isProcessing = false;

    public LoginPresenter(LoginView view) {
        this.loginView = view;
    }

    public void onLoginButtonClick(String email, String password) {

        if (isProcessing)
            return;

        // Reset errors.
        loginView.resetErrors();

        if (!isLoginValid(email, password))
            return;

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        isProcessing = true;

        loginView.showProgress(true);

        subscription = model.login(email, password)
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        isProcessing = false;
                        loginView.showProgress(false);
                        loginView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        isProcessing = false;
                        TokenStorage.saveToken(authResponse.getToken());
                        loginView.showProgress(false);
                        loginView.startMainActivity();
                    }
                });
    }

    public void onStop() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isLoginValid(String email, String password) {

        boolean valid = true;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            loginView.showPasswordError(R.string.error_invalid_password);

            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            loginView.showEmailError(R.string.error_field_required);

            valid = false;
        } else if (!isEmailValid(email)) {
            loginView.showEmailError(R.string.error_invalid_email);

            valid = false;
        }

        return valid;
    }
}
