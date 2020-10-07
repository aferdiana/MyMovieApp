package me.aldy.mylastsubmission.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class FavoriteHelper {
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen()) database.close();
    }

    public Cursor loadMovies(){
        return database.query(DatabaseContract.TABLE_FAV, null,
                "type=?", new String[]{"movie"}, null, null, DatabaseContract.FavColumns.TITLE + " ASC");
    }

    public Cursor loadTvShow(){
        return database.query(DatabaseContract.TABLE_FAV, null,
                "type=?", new String[]{"tv"}, null, null, DatabaseContract.FavColumns.TITLE + " ASC");
    }

    public Cursor isExist(String id){
        return database.rawQuery(("SELECT COUNT("+ _ID+") FROM "+DatabaseContract.TABLE_FAV+" WHERE "+ _ID+"=?"), new String[]{id});
    }

    public long insert(ContentValues contentValues){
        return database.insert(DatabaseContract.TABLE_FAV, null, contentValues);
    }

    public int delete(String id){
        return database.delete(DatabaseContract.TABLE_FAV, _ID+"=?", new String[]{id});
    }

    //provider
    public Cursor queryMovieProvider() {
        return database.query(DatabaseContract.TABLE_FAV
                , null
                , null
                , null
                , null
                , null
                , DatabaseContract.FavColumns.TITLE + " ASC");
    }

    public Cursor queryMovieProviderById(String id) {
        return database.query(DatabaseContract.TABLE_FAV
                , null
                , "type=?"+_ID+"=?"
                , new String[]{"movie",id}
                , null
                , null
                , DatabaseContract.FavColumns.TITLE + " ASC");
    }
}
