package com.telehuz.doittest.model;

import com.telehuz.doittest.model.data.AuthResponse;
import com.telehuz.doittest.model.data.Gif;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.model.data.UploadedImageResponse;

import java.io.File;

import rx.Observable;

public interface Model {

    Observable<AuthResponse> signup(String username, String email, String password, File avatarFile);

    Observable<AuthResponse> login(String username, String email);

    Observable<MyImages> get();

    Observable<Gif> gif();

    Observable<UploadedImageResponse> uploadImage(File imageFile, String description, String hashtag, float latitude, float longitude);
}
