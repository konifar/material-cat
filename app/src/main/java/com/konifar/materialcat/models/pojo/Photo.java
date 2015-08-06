package com.konifar.materialcat.models.pojo;

import com.google.gson.annotations.SerializedName;

public class Photo extends Model {

    @SerializedName("id")
    private String id;
    @SerializedName("owner")
    private String owner;
    @SerializedName("secret")
    private String secret;
    @SerializedName("server")
    private String server;
    @SerializedName("farm")
    private int farm;
    @SerializedName("title")
    private String title;
    @SerializedName("ispublic")
    private int isPublic;
    @SerializedName("isfriend")
    private int isFriend;
    @SerializedName("isfamily")
    private int isFamily;

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

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPublic() {
        return isPublic != 0;
    }

    public boolean isFriend() {
        return isFriend != 0;
    }

    public boolean isFamily() {
        return isFamily != 0;
    }

}
