package com.telehuz.doittest.view;

public interface SignupView {

    void showError(String error);

    void resetErrors();

    void showUsernameError(int error);

    void showEmailError(int error);

    void showPasswordError(int error);

    void showProgress(boolean show);

    void startMainActivity();

    boolean mayRequestStorage();

    void openGallery();
}
