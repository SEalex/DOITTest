package com.telehuz.doittest.view;

public interface LoginView {

    void showError(String error);

    void resetErrors();

    void showEmailError(int error);

    void showPasswordError(int error);

    void showProgress(boolean show);

    void startMainActivity();
}
