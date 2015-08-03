package com.konifar.materialcat.models.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSource extends Model {

    @SerializedName("photos")
    private Photos photos;
    @SerializedName("stat")
    private String stat;

    public List<Photo> getPhotos() {
        return photos.photo;
    }

    public class Photos extends Model {
        @SerializedName("page")
        private int page;
        @SerializedName("pages")
        private int pages;
        @SerializedName("perpage")
        private int perpage;
        @SerializedName("total")
        private String total;
        @SerializedName("photo")
        private List<Photo> photo;
    }

}
