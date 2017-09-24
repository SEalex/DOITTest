package com.telehuz.doittest.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadedImageResponse {

    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("smallImage")
    @Expose
    private String smallImage;
    @SerializedName("bigImage")
    @Expose
    private String bigImage;

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

}