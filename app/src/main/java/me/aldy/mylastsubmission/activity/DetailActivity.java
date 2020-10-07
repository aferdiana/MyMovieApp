package me.aldy.mylastsubmission.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.database.DatabaseContract;
import me.aldy.mylastsubmission.database.FavoriteHelper;
import me.aldy.mylastsubmission.model.Movie;
import me.aldy.mylastsubmission.model.TvShow;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";
    private static final String STATE_RESULT = "state_result";

    private boolean saved;
    private Button btnFav;
    private FavoriteHelper helper;

    private ActionBar actionBar;
    private ImageView imgPoster;
    private RatingBar ratingBar;
    private TextView tvTitle, tvRelease, tvGenre, tvRating, tvOverview;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESULT, saved);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btnFav = findViewById(R.id.btn_fav);
        btnFav.setText(getString(R.string.add_fav));

        actionBar = getSupportActionBar();
        imgPoster = findViewById(R.id.imageview_poster);
        ratingBar = findViewById(R.id.ratingBar);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release);
        tvGenre = findViewById(R.id.tv_genre);
        tvRating = findViewById(R.id.tv_rating);
        tvOverview = findViewById(R.id.tv_overview);

        helper = FavoriteHelper.getInstance(this);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            helper.open();
            getMovie();
        } else {
            helper.open();
            getTvShow();
        }

        if (savedInstanceState != null) saved = savedInstanceState.getBoolean(STATE_RESULT);

        setButton();
    }

    private void getMovie() {
        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        Cursor mCursor = helper.isExist(String.valueOf(movie.getId()));
        mCursor.moveToFirst();

        int cek = mCursor.getInt(0);

        if (actionBar != null) actionBar.setTitle(movie.getTitle());

        saved = cek > 0;

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(110, 200))
                .into(imgPoster);

        imgPoster.setContentDescription(movie.getTitle());
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(getString(R.string.movie_release, dateFormatter(movie.getRelease())));
        tvGenre.setText(getString(R.string.label_genre, movie.getGenre()));
        ratingBar.setRating(movie.getRating() / 2);
        tvRating.setText(String.valueOf(movie.getRating()));
        tvOverview.setText(getString(R.string.movie_overview, movie.getOverview()));

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!saved) {
                    insertItem();
                    Toast.makeText(DetailActivity.this, getString(R.string.added, movie.getTitle()), Toast.LENGTH_SHORT).show();
                    saved = true;
                    setButton();
                }
            }
        });
    }

    private void getTvShow() {
        final TvShow tv = getIntent().getParcelableExtra(EXTRA_TV);
        Cursor mCursor = helper.isExist(String.valueOf(tv.getId()));
        mCursor.moveToFirst();

        int cek = mCursor.getInt(0);

        if (actionBar != null) actionBar.setTitle(tv.getTitle());

        saved = cek > 0;

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + tv.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(110, 200))
                .into(imgPoster);

        imgPoster.setContentDescription(tv.getTitle());
        tvTitle.setText(tv.getTitle());
        tvRelease.setText(getString(R.string.movie_release, dateFormatter(tv.getRelease())));
        tvGenre.setText(getString(R.string.label_genre, tv.getGenre()));
        ratingBar.setRating(tv.getRating() / 2);
        tvRating.setText(String.valueOf(tv.getRating()));
        tvOverview.setText(getString(R.string.movie_overview, tv.getOverview()));

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!saved) {
                    insertItem();
                    saved = true;
                    setButton();
                }
            }
        });
    }


    private String dateFormatter(String date) {
        try {
            DateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat output = new SimpleDateFormat("d MMMM yyyy");
            Date target = input.parse(date);
            return output.format(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setButton(){
        if (saved){
            btnFav.setClickable(false);
            btnFav.setEnabled(false);
        }else {
            btnFav.setClickable(true);
            btnFav.setEnabled(true);
        }
    }

    private boolean insertItem(){
        String tipe;
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            Movie item = getIntent().getParcelableExtra(EXTRA_MOVIE);
            tipe = "movie";

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.FavColumns._ID, item.getId());
            cv.put(DatabaseContract.FavColumns.TITLE, item.getTitle());
            cv.put(DatabaseContract.FavColumns.OVERVIEW, item.getOverview());
            cv.put(DatabaseContract.FavColumns.RELEASE_DATE, item.getRelease());
            cv.put(DatabaseContract.FavColumns.RATING, item.getRating());
            cv.put(DatabaseContract.FavColumns.GENRE, item.getGenre());
            cv.put(DatabaseContract.FavColumns.POSTER, item.getPoster());
            cv.put(DatabaseContract.FavColumns.TYPE, tipe);

            long insert = helper.insert(cv);

            if (insert <= 0) return false;
            helper.close();
        }else {
            TvShow item = getIntent().getParcelableExtra(EXTRA_TV);
            tipe = "tv";

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.FavColumns._ID, item.getId());
            cv.put(DatabaseContract.FavColumns.TITLE, item.getTitle());
            cv.put(DatabaseContract.FavColumns.OVERVIEW, item.getOverview());
            cv.put(DatabaseContract.FavColumns.RELEASE_DATE, item.getRelease());
            cv.put(DatabaseContract.FavColumns.RATING, item.getRating());
            cv.put(DatabaseContract.FavColumns.GENRE, item.getGenre());
            cv.put(DatabaseContract.FavColumns.POSTER, item.getPoster());
            cv.put(DatabaseContract.FavColumns.TYPE, tipe);

            long insert = helper.insert(cv);

            if (insert <= 0) return false;
            helper.close();
        }
        return true;
    }

}

