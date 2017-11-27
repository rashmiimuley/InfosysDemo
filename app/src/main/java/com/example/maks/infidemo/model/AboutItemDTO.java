package com.example.maks.infidemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maks on 27/11/17.
 */

public class AboutItemDTO {
    @SerializedName("title")
    String title;
    @SerializedName("rows")
    List<AboutItem> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AboutItem> getRows() {
        return rows;
    }

    public void setRows(List<AboutItem> rows) {
        this.rows = rows;
    }
}
