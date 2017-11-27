package com.example.maks.infidemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maks on 27/11/17.
 */

public class AboutItem {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("imageHref")
    private String imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
