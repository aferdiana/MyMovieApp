package io.github.aferdiana.mymoviecatalogue;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView tvDetailTile, tvDetailOverview, tvDetailDuration, tvDetailRating, tvDetailRelease, tvDetailGenre;
    private ImageView imgDetailPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvDetailTile = findViewById(R.id.tv_title_detail);
        tvDetailGenre = findViewById(R.id.tv_genre);
        tvDetailRating = findViewById(R.id.tv_rating);
        tvDetailRelease = findViewById(R.id.tv_release);
        imgDetailPoster = findViewById(R.id.img_poster_detail);
        tvDetailDuration = findViewById(R.id.tv_duration);
        tvDetailOverview = findViewById(R.id.tv_overview_detail);

        ActionBar actionBar = getSupportActionBar();
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        actionBar.setTitle(movie.getTitle());

        tvDetailTile.setText(movie.getTitle());
        tvDetailGenre.setText(movie.getGenre());
        tvDetailRating.setText(movie.getRating());
        tvDetailRelease.setText("Release Date : " + movie.getDate());
        tvDetailDuration.setText("Duration : " + movie.getDuration());
        tvDetailOverview.setText(movie.getOverview());

        Glide.with(this).load(movie.getPoster()).into(imgDetailPoster);
    }
}
