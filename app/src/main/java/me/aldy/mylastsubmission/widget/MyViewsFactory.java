package me.aldy.mylastsubmission.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.concurrent.ExecutionException;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.model.Movie;

import static me.aldy.mylastsubmission.database.DatabaseContract.FavColumns.CONTENT_URI;

public class MyViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private int widgetAppid;

    public MyViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.widgetAppid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0; else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Bitmap mybitmap =null;
        Movie movie = getItem(i);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        try {
            mybitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster())
                    .error(R.drawable.ic_broken_image)
                    .submit()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageView, mybitmap);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private Movie getItem(int position) {
        if (!cursor.moveToPosition(position)) throw new IllegalStateException("Invalid Position");
        return new Movie(cursor);
    }
}
