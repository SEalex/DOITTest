package com.telehuz.doittest.model;

import android.net.Uri;

import com.telehuz.doittest.model.data.AuthResponse;

import java.io.File;

import rx.Observable;

public interface Model {

    Observable<AuthResponse> signup(String username, String email, String password, File avatarFile);

    Observable<AuthResponse> login(String username, String email);
}
