package me.aldy.mylastsubmission.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmoviequ";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAV = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s FLOAT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAV,
            DatabaseContract.FavColumns._ID,
            DatabaseContract.FavColumns.TITLE,
            DatabaseContract.FavColumns.OVERVIEW,
            DatabaseContract.FavColumns.RELEASE_DATE,
            DatabaseContract.FavColumns.RATING,
            DatabaseContract.FavColumns.GENRE,
            DatabaseContract.FavColumns.POSTER,
            DatabaseContract.FavColumns.TYPE
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAV);
        onCreate(db);
    }
}
