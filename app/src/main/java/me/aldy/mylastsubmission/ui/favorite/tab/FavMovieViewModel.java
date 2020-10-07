package me.aldy.mylastsubmission.ui.favorite.tab;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import me.aldy.mylastsubmission.database.DatabaseContract;

import me.aldy.mylastsubmission.database.FavoriteHelper;
import me.aldy.mylastsubmission.model.Movie;

public class FavMovieViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private FavoriteHelper helper;

    public FavMovieViewModel(Application application) {
        super(application);
        helper = FavoriteHelper.getInstance(application);
    }

    public void setMovies() {
        helper.open();
        ArrayList<Movie> arrayList =  new ArrayList<>();
        Cursor cursor = helper.loadMovies();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.FavColumns._ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.TITLE));
            String overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.OVERVIEW));
            String release = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.RELEASE_DATE));
            float rating = cursor.getFloat(cursor.getColumnIndex(DatabaseContract.FavColumns.RATING));
            String genre = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.GENRE));
            String poster = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.POSTER));

            Movie movie =  new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setOverview(overview);
            movie.setRelease(release);
            movie.setRating(rating);
            movie.setGenre(genre);
            movie.setPoster(poster);

            arrayList.add(movie);
        }
        listMovies.setValue(arrayList);
        cursor.close();
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}

