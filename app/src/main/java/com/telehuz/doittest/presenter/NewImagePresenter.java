package com.telehuz.doittest.presenter;

import android.util.Log;

import com.telehuz.doittest.model.Model;
import com.telehuz.doittest.model.ModelImpl;
import com.telehuz.doittest.model.TokenStorage;
import com.telehuz.doittest.model.data.UploadedImageResponse;
import com.telehuz.doittest.view.NewImageView;

import java.io.File;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class NewImagePresenter {

    private Model model = new ModelImpl(TokenStorage.getToken());
    private NewImageView newImageView;

    private Subscription subscription = Subscriptions.empty();

    private boolean isProcessing = false;

    public NewImagePresenter(NewImageView newImageView) {
        this.newImageView = newImageView;
    }

    public void onAddImageClick() {
        if (newImageView.mayRequestStorage()) {
            newImageView.openGallery();
        }
    }

    public void onLocationNotFound() {
        isProcessing = false;
    }

    public void onDoneButtonClick() {

        if (isProcessing)
            return;

        isProcessing = true;

        if (newImageView.mayRequestLocation()) {
            newImageView.findLocation();
        } else {
            isProcessing = false;
        }
    }

    public void uploadImage(File path, String description, String tag, float latitude, float longitude) {

        if (path == null || path.getAbsolutePath().isEmpty()) {
            newImageView.showError("Image is required");

            isProcessing = false;

            return;
        }

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        newImageView.showProgress(true);

        subscription = model.uploadImage(path, description, tag, latitude, longitude).subscribe(new Observer<UploadedImageResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                isProcessing = false;
                newImageView.showProgress(false);
                newImageView.showError(e.getMessage());

            }

            @Override
            public void onNext(UploadedImageResponse responseBody) {
                isProcessing = false;
                newImageView.showProgress(false);
                newImageView.close();
            }
        });
    }

    public void onStop() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
