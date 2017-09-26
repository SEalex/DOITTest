package com.telehuz.doittest.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.telehuz.doittest.DOITTestApplication;

public class TokenStorage {

    private static final String NAME = "com.telehuz.doittest";
    private static final String TOKEN = "com.telehuz.doittest.token";

    public static void saveToken(String token) {
        SharedPreferences prefs = DOITTestApplication.getInstance()
                .getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(TOKEN, token).apply();
    }

    public static String getToken() {
        SharedPreferences prefs = DOITTestApplication.getInstance()
                .getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN, "");
    }
}
