package com.telehuz.doittest.view;

import com.telehuz.doittest.model.data.MyImages;

public interface MainView {

    void showImages(MyImages images);

    void openNewImageFragment();

    void showGif(String url);

    void showGifLayout();

    void closeGif();

    boolean isGifLayoutVisible();

    void goBack();

    void showProgress(boolean show);

    void showError(String error);
}
