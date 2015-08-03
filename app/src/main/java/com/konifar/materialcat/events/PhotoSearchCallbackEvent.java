package com.konifar.materialcat.events;

import com.konifar.materialcat.models.pojo.Photo;

import java.util.List;

public class PhotoSearchCallbackEvent {

    private boolean success;
    private List<Photo> photos;
    private String sort;

    public PhotoSearchCallbackEvent(boolean success, String sort) {
        this.success = success;
        this.sort = sort;
    }

    public PhotoSearchCallbackEvent(boolean success, List<Photo> photos, String sort) {
        this.success = success;
        this.photos = photos;
        this.sort = sort;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getSort() {
        return sort;
    }

}
