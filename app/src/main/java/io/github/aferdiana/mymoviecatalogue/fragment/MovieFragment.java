package io.github.aferdiana.mymoviecatalogue.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.aferdiana.mymoviecatalogue.R;
import io.github.aferdiana.mymoviecatalogue.adapter.MovieAdapter;
import io.github.aferdiana.mymoviecatalogue.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovie;
    private MovieAdapter adapter;
    private ArrayList<Movie> list;
    private String[] dataTitle, dataOverview, dataRating, dataDuration, dataGenre, dataRelease;
    private TypedArray dataPoster;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);

        showRecyclerList();
    }

    private void showRecyclerList() {
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter(getActivity());
        adapter.setListMovie(list);
        rvMovie.setAdapter(adapter);
        init();
        addItem();
    }

    private void init() {
        dataGenre = getResources().getStringArray(R.array.data_movie_genre);
        dataTitle = getResources().getStringArray(R.array.data_movie_title);
        dataPoster = getResources().obtainTypedArray(R.array.data_movie_poster);
        dataRating = getResources().getStringArray(R.array.data_movie_rating);
        dataRelease = getResources().getStringArray(R.array.data_movie_release);
        dataOverview = getResources().getStringArray(R.array.data_movie_overview);
        dataDuration = getResources().getStringArray(R.array.data_movie_duration);
    }

    private void addItem() {
        list = new ArrayList<>();

        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setDate(dataRelease[i]);
            movie.setGenre(dataGenre[i]);
            movie.setTitle(dataTitle[i]);
            movie.setRating(dataRating[i]);
            movie.setDuration(dataDuration[i]);
            movie.setOverview(dataOverview[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            list.add(movie);
        }
        adapter.setListMovie(list);
    }

}
