package com.telehuz.doittest.presenter;

import java.io.File;

public interface SignupPresenter {

    void onSignupButtonClick(String username, String email, String password, File avatarFile);

    void onStop();
}
