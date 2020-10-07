package me.aldy.mylastsubmission.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.aldy.mylastsubmission.database.DatabaseContract;

import static me.aldy.mylastsubmission.database.DatabaseContract.getColumnFloat;
import static me.aldy.mylastsubmission.database.DatabaseContract.getColumnInt;
import static me.aldy.mylastsubmission.database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("genre_ids")
    private List<Integer> genreId;

    private String genre;

    private String type;

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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getGenreId() {
        return genreId;
    }

    public void setGenreId(List<Integer> genreId) {
        this.genreId = genreId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeString(this.release);
        dest.writeString(this.poster);
        dest.writeFloat(this.rating);
        dest.writeList(this.genreId);
        dest.writeString(this.genre);
        dest.writeString(this.type);
    }

    public Movie() {
    }

    public Movie(int id, String title, String overview, String release, String genre, String poster, String type, float rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release = release;
        this.genre = genre;
        this.poster = poster;
        this.type = type;
        this.rating = rating;
    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.FavColumns._ID);
        this.title = getColumnString(cursor, DatabaseContract.FavColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumns.OVERVIEW);
        this.release = getColumnString(cursor, DatabaseContract.FavColumns.RELEASE_DATE);
        this.genre = getColumnString(cursor, DatabaseContract.FavColumns.GENRE);
        this.poster = getColumnString(cursor, DatabaseContract.FavColumns.POSTER);
        this.type = getColumnString(cursor, DatabaseContract.FavColumns.TYPE);
        this.rating = getColumnFloat(cursor, DatabaseContract.FavColumns.RATING);
    }



    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.release = in.readString();
        this.poster = in.readString();
        this.rating = in.readFloat();
        this.genreId = new ArrayList<Integer>();
        in.readList(this.genreId, Integer.class.getClassLoader());
        this.genre = in.readString();
        this.type = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
