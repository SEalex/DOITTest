package com.telehuz.doittest.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gif {

    @SerializedName("gif")
    @Expose
    private String gif;

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

}