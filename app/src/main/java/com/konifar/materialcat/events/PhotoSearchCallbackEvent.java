package com.konifar.materialcat.events;

import com.konifar.materialcat.models.pojo.Photo;

import java.util.List;

public class PhotoSearchCallbackEvent {

    private boolean success;
    private List<Photo> photos;

    public PhotoSearchCallbackEvent(boolean success) {
        this.success = success;
    }

    public PhotoSearchCallbackEvent(boolean success, List<Photo> photos) {
        this.success = success;
        this.photos = photos;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

}
