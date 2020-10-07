package me.aldy.mylastsubmission.ui.movie;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.activity.DetailActivity;
import me.aldy.mylastsubmission.adapter.MovieAdapter;
import me.aldy.mylastsubmission.model.Genre;
import me.aldy.mylastsubmission.model.Movie;

public class MovieFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.movie_fragment, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        rvMovie = root.findViewById(R.id.rv_movies);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        MovieViewModel movieViewModel;
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.setMovies();
        movieViewModel.setGenres();
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    adapter.setListMovie(movies);
                    showLoading(false);
                }else if(adapter.getListMovie().isEmpty()) {
                    showError();
                }
            }
        });

        movieViewModel.getgenres().observe(this, new Observer<ArrayList<Genre>>() {
            @Override
            public void onChanged(ArrayList<Genre> genres) {
                if (genres != null){
                    adapter.setListGenre(genres);
                }
            }
        });


        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                showDetailMovie(movie);
            }
        });

        showLoading(true);
    }

    private void showDetailMovie(Movie movie){
        Movie object = new Movie();
        object.setId(movie.getId());
        object.setTitle(movie.getTitle());
        object.setPoster(movie.getPoster());
        object.setRelease(movie.getRelease());
        object.setGenre(movie.getGenre());
        object.setOverview(movie.getOverview());
        object.setRating(movie.getRating());
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, object);
        getActivity().startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showError(){
        Toast.makeText(getActivity(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        showLoading(false);
    }
}
