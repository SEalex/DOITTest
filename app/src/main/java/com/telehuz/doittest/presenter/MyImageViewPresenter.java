package com.telehuz.doittest.presenter;

import android.support.annotation.NonNull;

import com.telehuz.doittest.model.data.Image;
import com.telehuz.doittest.view.MyImageView;

public class MyImageViewPresenter {

    private MyImageView myImageView;
    private Image model;

    public MyImageViewPresenter(Image model) {
        this.model = model;
    }

    public void bindView(@NonNull MyImageView view) {
        this.myImageView = view;
        if (setupDone()) {
            updateView();
        }
    }

    public void updateView() {
        myImageView.setAddress(model.getParameters().getAddress());
        myImageView.setImage(model.getSmallImagePath());
        myImageView.setWeather(model.getParameters().getWeather());
    }

    protected boolean setupDone() {
        return myImageView != null && model != null;
    }
}
