package com.telehuz.doittest.presenter;

import com.telehuz.doittest.view.StartView;

public class StartPresenter {

    private StartView startView;

    public StartPresenter(StartView startView) {
        this.startView = startView;
    }

    public void onLoginButtonClick() {
        startView.openLoginFragment();
    }

    public void onSignupButtonClick() {
        startView.openSignupFragment();
    }
}
