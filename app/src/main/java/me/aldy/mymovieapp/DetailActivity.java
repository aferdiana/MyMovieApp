package me.aldy.mymovieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.aldy.mymovieapp.db.FavDatabase;
import me.aldy.mymovieapp.model.Movie;
import me.aldy.mymovieapp.model.TvShow;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";
    private static final String STATE_RESULT = "state_result";
    private FavDatabase db;
    private boolean saved;
    private Button btnFav;

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

        db = Room.databaseBuilder(
                getApplicationContext(),
                FavDatabase.class,
                "dbFav").allowMainThreadQueries()
                .build();


        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            getMovie();
        } else {
            getTvShow();
        }

        if (savedInstanceState != null)saved = savedInstanceState.getBoolean(STATE_RESULT);

        setButton();
    }

    private void getMovie() {
        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        Movie cek = db.favDao().selectMovie(String.valueOf(movie.getId()));

        ActionBar actionBar = getSupportActionBar();
        ImageView imgPoster = findViewById(R.id.imageview_poster);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvRelease = findViewById(R.id.tv_release);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvRating = findViewById(R.id.tv_rating);
        TextView tvOverview = findViewById(R.id.tv_overview);

        if (movie != null) {
            if (actionBar != null) actionBar.setTitle(movie.getTitle());

            if (cek == null) saved = false; else saved = true;

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
                        insertMovie(movie);
                        saved = true;
                        setButton();
                    }
                }
            });
        }
    }

    private void getTvShow() {
        final TvShow tv = getIntent().getParcelableExtra(EXTRA_TV);

        ActionBar actionBar = getSupportActionBar();
        ImageView imgPoster = findViewById(R.id.imageview_poster);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvRelease = findViewById(R.id.tv_release);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvRating = findViewById(R.id.tv_rating);
        TextView tvOverview = findViewById(R.id.tv_overview);

        if (tv != null) {
            if (actionBar != null) actionBar.setTitle(tv.getTitle());

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
                        insertTv(tv);
                        saved = true;
                        setButton();
                    }
                }
            });
        }
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

    private void insertMovie(final Movie movie){
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return db.favDao().insertMovie(movie);
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(DetailActivity.this, getString(R.string.added, movie.getTitle()), Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void insertTv(final TvShow tv){
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return db.favDao().insertTv(tv);
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(DetailActivity.this, getString(R.string.added, tv.getTitle()), Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

}
