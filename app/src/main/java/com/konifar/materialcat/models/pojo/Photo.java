package com.konifar.materialcat.models.pojo;

import com.google.gson.annotations.SerializedName;

public class Photo extends Model {

    @SerializedName("id")
    private String id;
    @SerializedName("secret")
    private String secret;
    @SerializedName("server")
    private String server;
    @SerializedName("farm")
    private int farm;
    @SerializedName("title")
    private String title;

    public String getImageUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://farm");
        sb.append(farm);
        sb.append(".staticflickr.com/");
        sb.append(server);
        sb.append("/");
        sb.append(id);
        sb.append("_");
        sb.append(secret);
        sb.append(".jpg");

        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

}
