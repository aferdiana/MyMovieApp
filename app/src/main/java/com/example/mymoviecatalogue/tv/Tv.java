package com.example.mymoviecatalogue.tv;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Tv implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String poster;
    private String release_date;
    private float rating;

    Tv(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("original_name");
            String poster = object.getString("poster_path");
            String release = object.getString("first_air_date");
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


    public Tv() {

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

    protected Tv(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.release_date = in.readString();
        this.rating = in.readFloat();
    }

    public static final Parcelable.Creator<Tv> CREATOR = new Parcelable.Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };
}
