package com.telehuz.doittest.model.api;

import com.telehuz.doittest.model.data.AuthResponse;
import com.telehuz.doittest.model.data.Image;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.model.data.UploadedImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface ApiInterface {

    @Multipart
    @POST("create")
    Observable<AuthResponse> signup(@Part("username") RequestBody username,
                                    @Part("email") RequestBody email,
                                    @Part("password") RequestBody password,
                                    @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("login")
    Observable<AuthResponse> login(@Field("email") String username, @Field("password") String email);

    @GET("all")
    Observable<MyImages> get();

    @GET("gif")
    Observable<ResponseBody> gif();

    @Multipart
    @POST("image")
    Observable<UploadedImageResponse> uploadImage(@Part MultipartBody.Part image,
                                                  @Part("description") RequestBody description,
                                                  @Part("hashtag") RequestBody hashtag,
                                                  @Part("latitude") RequestBody latitude,
                                                  @Part("longitude") RequestBody longitude);
}