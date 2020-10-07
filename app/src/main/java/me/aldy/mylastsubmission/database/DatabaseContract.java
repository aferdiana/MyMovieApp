package me.aldy.mylastsubmission.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAV = "favorite";

    //provider
    public static final String AUTHORITY = "me.aldy.mylastsubmission";
    private static final String SCHEME = "content";

    public static class FavColumns implements BaseColumns {
        public final static String TITLE = "title";
        public final static String OVERVIEW = "overview";
        public final static String RELEASE_DATE = "release";
        public final static String RATING = "rating";
        public final static String GENRE = "genre";
        public final static String POSTER = "poster";
        public final static String TYPE = "type";

        //provider
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAV)
                .build();
    }

    //provider
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
