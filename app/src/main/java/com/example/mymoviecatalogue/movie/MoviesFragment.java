package com.example.mymoviecatalogue.movie;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviecatalogue.R;

import java.util.ArrayList;


public class MoviesFragment extends Fragment {
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter = new MovieAdapter();

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {

        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setListMovie(movies);
                showLoading(false);
            }
        }
    };


    public MoviesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView rvMovie;
        rvMovie = getActivity().findViewById(R.id.rv_movies);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        MovieViewModel movieViewModel;
        movieViewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        movieViewModel.setMovie();
        movieViewModel.getMovies().observe(getActivity(), getMovie);

        showLoading(true);
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
