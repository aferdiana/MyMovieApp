package me.aldy.mymovieapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import me.aldy.mymovieapp.model.Movie;
import me.aldy.mymovieapp.model.TvShow;

@Dao
public interface FavDao {
    @Query("SELECT * FROM Movies")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM TvShow")
    List<TvShow> getAllTv();

    @Insert
    long insertMovie(Movie movie);

    @Insert
    long insertTv(TvShow tvShow);

    @Delete
    void removeMovie(Movie movie);

    @Delete
    void removeTv(TvShow tvShow);

    @Query("SELECT * FROM Movies WHERE id = :id")
    Movie selectMovie(String id);

    @Query("SELECT * FROM TvShow WHERE id = :id")
    TvShow selectTv(String id);
}
