package io.github.aferdiana.mymoviecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import io.github.aferdiana.mymoviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(movie.getTitle());

        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvRating = findViewById(R.id.tv_rating);
        TextView tvRelease = findViewById(R.id.tv_date_release);
        TextView tvDuration = findViewById(R.id.tv_duration);
        TextView tvOverview = findViewById(R.id.tv_overview);
        ImageView imgPoster = findViewById(R.id.img_poster);

        tvGenre.setText(movie.getGenre());
        tvTitle.setText(movie.getTitle());
        tvRating.setText(movie.getRating());
        tvRelease.setText(movie.getDate());
        tvDuration.setText(movie.getDuration());
        tvOverview.setText(movie.getOverview());
        imgPoster.setContentDescription(movie.getTitle());
        Picasso.get()
                .load(movie.getPoster())
                .resize(110, 160)
                .centerCrop()
                .into(imgPoster);
    }
}
