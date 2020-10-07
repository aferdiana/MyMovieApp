package com.example.mymoviecatalogue.tv;


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


public class TvShowsFragment extends Fragment {
    private ProgressBar progressBar;
    private TvShowAdapter movieAdapter = new TvShowAdapter();


    private Observer<ArrayList<Tv>> getTvMovie = new Observer<ArrayList<Tv>>() {

        @Override
        public void onChanged(ArrayList<Tv> movies) {
            if (movies != null) {
                movieAdapter.setListMovie(movies);
                showLoading(false);
            }
        }
    };

    public TvShowsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView rvMovie;
        rvMovie = getActivity().findViewById(R.id.rv_tvmovies);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        TVsViewModel movieViewModel;
        movieViewModel = ViewModelProviders.of(getActivity()).get(TVsViewModel.class);
        movieViewModel.setTvShow();
        movieViewModel.getTvs().observe(getActivity(), getTvMovie);

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
