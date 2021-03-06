package me.aldy.mylastsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenreResponse {

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
