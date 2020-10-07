package io.github.aferdiana.mymoviecatalogue;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] dataTitle, dataOverview, dataRating, dataDuration, dataGenre, dataRelease;
    private TypedArray dataPoster;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieAdapter(this);

        ListView listView = findViewById(R.id.lv_movies);
        listView.setAdapter(adapter);

        init();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie objMove = new Movie();
                objMove.setDate(movies.get(i).getDate());
                objMove.setTitle(movies.get(i).getTitle());
                objMove.setGenre(movies.get(i).getGenre());
                objMove.setRating(movies.get(i).getRating());
                objMove.setPoster(movies.get(i).getPoster());
                objMove.setDuration(movies.get(i).getDuration());
                objMove.setOverview(movies.get(i).getOverview());
                Intent intentMovie = new Intent(MainActivity.this, DetailMovieActivity.class);
                intentMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, objMove);
                startActivity(intentMovie);
            }
        });
    }

    private void init() {
        dataGenre = getResources().getStringArray(R.array.data_genre);
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
        dataRating = getResources().getStringArray(R.array.data_rating);
        dataRelease = getResources().getStringArray(R.array.data_date_release);
        dataOverview = getResources().getStringArray(R.array.data_overview);
        dataDuration = getResources().getStringArray(R.array.data_duration);
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setGenre(dataGenre[i]);
            movie.setTitle(dataTitle[i]);
            movie.setDate(dataRelease[i]);
            movie.setRating(dataRating[i]);
            movie.setDuration(dataDuration[i]);
            movie.setOverview(dataOverview[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }
}
