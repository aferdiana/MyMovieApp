package me.aldy.mymovieapp.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.aldy.mymovieapp.model.Movie;
import me.aldy.mymovieapp.model.TvShow;

@Database(entities = {Movie.class, TvShow.class}, version = 1, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {
    public abstract FavDao favDao();
}
