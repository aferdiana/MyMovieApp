package me.aldy.mylastsubmission.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponse {
    @SerializedName("results")
    private ArrayList<TvShow> tvShows;

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    public void setMovies(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
    }
}
