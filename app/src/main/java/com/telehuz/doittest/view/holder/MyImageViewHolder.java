package com.telehuz.doittest.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.telehuz.doittest.DOITTestApplication;
import com.telehuz.doittest.R;
import com.telehuz.doittest.presenter.MyImageViewPresenter;
import com.telehuz.doittest.view.MyImageView;

public class MyImageViewHolder extends RecyclerView.ViewHolder implements MyImageView {

    ImageView imagePicture;
    TextView textWeather;
    TextView textAddress;

    MyImageViewPresenter presenter;

    public MyImageViewHolder(View itemView) {
        super(itemView);

        imagePicture = itemView.findViewById(R.id.image_pic);
        textWeather = itemView.findViewById(R.id.text_weather);
        textAddress = itemView.findViewById(R.id.text_address);
    }

    public void bindPresenter(MyImageViewPresenter presenter) {
        this.presenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        this.presenter = null;
    }

    public void setImage(String url) {
        Picasso.with(DOITTestApplication.getInstance().getApplicationContext())
                .load(url)
                .into(imagePicture);
    }

    public void setWeather(String weather) {
        textWeather.setText(weather);
    }

    public void setAddress(String address) {
        textAddress.setText(address);
    }
}
