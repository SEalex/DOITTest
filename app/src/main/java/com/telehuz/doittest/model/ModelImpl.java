package com.telehuz.doittest.model;

import com.telehuz.doittest.model.api.ApiInterface;
import com.telehuz.doittest.model.api.ApiModule;
import com.telehuz.doittest.model.data.AuthResponse;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.model.data.UploadedImageResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModelImpl implements Model {

    private ApiInterface apiInterface;

    public ModelImpl() {
         apiInterface = ApiModule.getApiInterface();
    }

    public ModelImpl(String token) {
        apiInterface = ApiModule.getApiInterface(token);
    }

    @Override
    public Observable<AuthResponse> signup(String username, String email, String password, File avatarFile) {

        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), avatarFile);
        MultipartBody.Part avatarBody = MultipartBody.Part.createFormData("avatar", "ava", requestFile);

        return apiInterface.signup(usernameBody, emailBody, passwordBody, avatarBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AuthResponse> login(String username, String email) {

        return apiInterface.login(username, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MyImages> get() {
        return apiInterface.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    Observable<ResponseBody> gif();

    @Override
    public Observable<UploadedImageResponse> uploadImage(File imageFile, String description, String hashtag,
                                                         float latitude, float longitude) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", "pic", requestFile);

        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody hashtagBody = RequestBody.create(MediaType.parse("text/plain"), hashtag);
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("text/plain"), Float.toString(latitude));
        RequestBody longitudeBody = RequestBody.create(MediaType.parse("text/plain"), Float.toString(longitude));

        return apiInterface.uploadImage(imageBody, descriptionBody, hashtagBody, latitudeBody, longitudeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
