package me.aldy.mylastsubmission.ui.favorite.tab;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import me.aldy.mylastsubmission.database.DatabaseContract;
import me.aldy.mylastsubmission.database.FavoriteHelper;
import me.aldy.mylastsubmission.model.TvShow;

public class FavTvViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();
    private FavoriteHelper helper;

    public FavTvViewModel(Application application) {
        super(application);
        helper = FavoriteHelper.getInstance(application);
    }

    public void setTvShows() {
        helper.open();
        ArrayList<TvShow> arrayList =  new ArrayList<>();
        Cursor cursor = helper.loadTvShow();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.FavColumns._ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.TITLE));
            String overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.OVERVIEW));
            String release = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.RELEASE_DATE));
            float rating = cursor.getFloat(cursor.getColumnIndex(DatabaseContract.FavColumns.RATING));
            String genre = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.GENRE));
            String poster = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavColumns.POSTER));

            TvShow tv =  new TvShow();
            tv.setId(id);
            tv.setTitle(title);
            tv.setOverview(overview);
            tv.setRelease(release);
            tv.setRating(rating);
            tv.setGenre(genre);
            tv.setPoster(poster);

            arrayList.add(tv);
        }
        listTvShows.setValue(arrayList);
        cursor.close();
    }

    public LiveData<ArrayList<TvShow>> getTvShows() {
        return listTvShows;
    }
}

