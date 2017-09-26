package com.telehuz.doittest.view;

public interface NewImageView {

    void openGallery();

    boolean mayRequestLocation();

    boolean mayRequestStorage();

    void showProgress(boolean show);

    void showError(String error);

    void close();

    void findLocation();
}
