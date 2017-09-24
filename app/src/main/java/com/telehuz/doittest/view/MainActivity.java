package com.telehuz.doittest.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.telehuz.doittest.R;
import com.telehuz.doittest.model.Model;
import com.telehuz.doittest.model.ModelImpl;
import com.telehuz.doittest.model.data.Image;
import com.telehuz.doittest.model.data.MyImages;
import com.telehuz.doittest.model.data.UploadedImageResponse;
import com.telehuz.doittest.util.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_RESULT = 0;

    @BindView(R.id.button_add)
    ImageView addButton;

    @BindView(R.id.button_gif)
    ImageView gifButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addButton.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_RESULT);
        });

        gifButton.setOnClickListener(v -> {

            Model model = new ModelImpl("8020e668554e5009f4a09af5e609d3d3");

            model.get().subscribe(new Observer<MyImages>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("error", e.getMessage());
                }

                @Override
                public void onNext(MyImages response) {
                   for (Image pic : response.getImages()) {
                       Log.d("ok", " id: " + pic.getId()
                               + ", descr: " + pic.getDescription()
                               + ", tag: " + pic.getHashtag()
                               + ", small im: " + pic.getSmallImagePath()
                               + ", big im: " + pic.getBigImagePath()
                               + ", created: " + pic.getCreated()

                               + ", long: " + pic.getParameters().getLongitude()
                               + ", lat: " + pic.getParameters().getLatitude()
                               + ", address: " + pic.getParameters().getAddress()
                               + ", weather: " + pic.getParameters().getWeather());
                   }

                }
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            File imageFile = FileUtils.getFile(this, uri);

            Model model = new ModelImpl("8020e668554e5009f4a09af5e609d3d3");

            model.uploadImage(imageFile, "first image", "#doit", 40.7214f, -74.0052f).subscribe(new Observer<UploadedImageResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("error", e.getMessage());

                }

                @Override
                public void onNext(UploadedImageResponse responseBody) {
                    Log.d("ok", " address: " + responseBody.getParameters().getAddress()
                            + ", weather: " + responseBody.getParameters().getWeather()
                            + ", small im: " + responseBody.getSmallImage()
                            + ", big im: " + responseBody.getBigImage());
                }
            });
        }
    }
}
