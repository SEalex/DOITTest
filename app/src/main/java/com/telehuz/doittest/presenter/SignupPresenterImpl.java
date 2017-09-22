package com.telehuz.doittest.presenter;

import android.text.TextUtils;

import com.telehuz.doittest.R;
import com.telehuz.doittest.model.Model;
import com.telehuz.doittest.model.ModelImpl;
import com.telehuz.doittest.model.data.AuthResponse;
import com.telehuz.doittest.view.SignupView;

import java.io.File;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class SignupPresenterImpl implements SignupPresenter {

    private Model model = new ModelImpl();
    private SignupView signupView;

    private Subscription subscription = Subscriptions.empty();

    private boolean isProcessing = false;

    public SignupPresenterImpl(SignupView view) {
        this.signupView = view;
    }

    @Override
    public void onSignupButtonClick(String username, String email, String password, File avatar) {

        if (isProcessing)
            return;

        // Reset errors.
        signupView.resetErrors();

        if (!isSignupValid(username, email, password, avatar))
            return;

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        isProcessing = true;

        signupView.showProgress(true);

        subscription = model.signup(username, email, password, avatar)
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        isProcessing = false;
                        signupView.showProgress(false);
                        signupView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        isProcessing = false;
                        signupView.showProgress(false);
                        signupView.startMainActivity();
                    }
                });
    }

    @Override
    public void onStop() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isSignupValid(String username, String email, String password, File avatar) {

        boolean valid = true;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            signupView.showPasswordError(R.string.error_invalid_password);

            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            signupView.showEmailError(R.string.error_field_required);

            valid = false;
        } else if (!isEmailValid(email)) {
            signupView.showEmailError(R.string.error_invalid_email);

            valid = false;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            signupView.showUsernameError(R.string.error_field_required);

            valid = false;
        } else if (!isUsernameValid(username)) {
            signupView.showUsernameError(R.string.error_invalid_username);

            valid = false;
        }

        if (avatar == null || avatar.getAbsolutePath().isEmpty()) {
            signupView.showError("User avatar is required");

            valid = false;
        }

        return valid;
    }
}
