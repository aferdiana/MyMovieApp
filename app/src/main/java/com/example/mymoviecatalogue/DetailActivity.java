package com.example.mymoviecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.movie.Movie;
import com.example.mymoviecatalogue.tv.Tv;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().hasExtra(EXTRA_MOVIE)){
            getMovie();
        }else{
            getTvShow();
        }
    }

    private void getMovie(){
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(movie.getTitle());

        ImageView imgPoster = findViewById(R.id.imageview_poster);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvRelease = findViewById(R.id.release_dummy);
        TextView tvRating = findViewById(R.id.dummy_rating);
        TextView tvOverview = findViewById(R.id.tv_overview);

        Glide.with(this)
                .load(movie.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(110, 200))
                .into(imgPoster);
        imgPoster.setContentDescription(movie.getTitle());
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease_date());
        tvRating.setText(String.valueOf(movie.getRating()));
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating(movie.getRating() / 2);
    }

    private void getTvShow(){
        Tv movie = getIntent().getParcelableExtra(EXTRA_TV);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(movie.getTitle());

        ImageView imgPoster = findViewById(R.id.imageview_poster);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvRelease = findViewById(R.id.release_dummy);
        TextView tvRating = findViewById(R.id.dummy_rating);
        TextView tvOverview = findViewById(R.id.tv_overview);

        Glide.with(this)
                .load(movie.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(110, 200))
                .into(imgPoster);
        imgPoster.setContentDescription(movie.getTitle());
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease_date());
        tvRating.setText(String.valueOf(movie.getRating()));
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating(movie.getRating() / 2);
    }
}
