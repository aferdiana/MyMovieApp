package me.aldy.mylastsubmission.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aldy.mylastsubmission.database.DatabaseContract;
import me.aldy.mylastsubmission.database.FavoriteHelper;

import static me.aldy.mylastsubmission.database.DatabaseContract.AUTHORITY;
import static me.aldy.mylastsubmission.database.DatabaseContract.TABLE_FAV;

public class MyContentProvider extends ContentProvider {
    //provider
    private static final int FAV = 1;
    private static final int FAV_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteHelper favHelper;
    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_FAV, FAV);
        sUriMatcher.addURI(AUTHORITY, TABLE_FAV + "/#", FAV_ID);
    }

    @Override
    public boolean onCreate() {
        favHelper = FavoriteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        favHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                cursor = favHelper.queryMovieProvider();
                break;
            case FAV_ID:
                cursor = favHelper.queryMovieProviderById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null)cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added = 0;

        switch (sUriMatcher.match(uri)){
            case FAV:
                added = favHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(DatabaseContract.FavColumns.CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted = 0;
        switch (sUriMatcher.match(uri)){
            case FAV_ID:
                deleted = favHelper.delete(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
