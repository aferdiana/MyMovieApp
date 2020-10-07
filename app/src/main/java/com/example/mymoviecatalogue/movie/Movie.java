package com.example.mymoviecatalogue.movie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private int id;
    private String title;
    private String overview;
    private String poster;
    private String release_date;
    private float rating;

    Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("original_title");
            String poster = object.getString("poster_path");
            String release = object.getString("release_date");
            String overview = object.getString("overview");
            float rating = Float.parseFloat(object.getString("vote_average"));
            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster = "https://image.tmdb.org/t/p/w185/" + poster;
            this.release_date = release;
            this.rating = rating;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.release_date = in.readString();
        this.rating = in.readFloat();
    }

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.release_date);
        dest.writeFloat(this.rating);
    }
}
