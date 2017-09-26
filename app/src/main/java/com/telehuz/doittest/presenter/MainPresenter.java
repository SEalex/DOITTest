package com.telehuz.doittest.presenter;

import com.telehuz.doittest.model.Model;
import com.telehuz.doittest.model.ModelImpl;
import com.telehuz.doittest.model.TokenStorage;
import com.telehuz.doittest.model.data.Gif;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.view.MainView;

import rx.Observer;

public class MainPresenter {

    private Model model = new ModelImpl(TokenStorage.getToken());
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void loadImages() {

        mainView.showProgress(true);

        model.get().subscribe(new Observer<MyImages>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mainView.showProgress(false);
                mainView.showError(e.getMessage());
            }

            @Override
            public void onNext(MyImages myImages) {
                mainView.showProgress(false);
                mainView.showImages(myImages);
            }
        });
    }

    public void onAddImageClick() {
        mainView.openNewImageFragment();
    }

    public void onShowGifClick() {

        mainView.showGifLayout();
        mainView.showProgress(true);

        model.gif().subscribe(new Observer<Gif>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mainView.showProgress(false);
            }

            @Override
            public void onNext(Gif gif) {

                mainView.showGif(gif.getGif());
            }
        });
    }

    public void onBackPressed() {
        if (mainView.isGifLayoutVisible()) {
            mainView.showProgress(false);
            mainView.closeGif();
        } else {
            mainView.goBack();
        }
    }
}
