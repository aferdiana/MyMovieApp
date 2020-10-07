package me.aldy.mylastsubmission.ui.search;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import me.aldy.mylastsubmission.activity.MainActivity;
import me.aldy.mylastsubmission.adapter.MovieAdapter;
import me.aldy.mylastsubmission.adapter.TvAdapter;
import me.aldy.mylastsubmission.model.Genre;
import me.aldy.mylastsubmission.model.Movie;
import me.aldy.mylastsubmission.model.TvShow;

public class SearchFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieAdapter adapterMovie;
    private TvAdapter adapterTv;

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

        SearchViewModel searchViewModel;
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (MainActivity.tipe.equals("movie")){
            searchViewModel.setMovies();
            searchViewModel.setGenres();
            adapterMovie = new MovieAdapter();
            adapterMovie.notifyDataSetChanged();
            rvMovie.setAdapter(adapterMovie);
            searchViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    if (movies != null) {
                        adapterMovie.setListMovie(movies);
                        showLoading(false);
                    }else if(adapterMovie.getListMovie().isEmpty()) {
                        showError();
                    }
                }
            });

            adapterMovie.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
                @Override
                public void onItemClicked(Movie movie) {
                    showDetailMovie(movie);
                }
            });

            searchViewModel.getgenres().observe(this, new Observer<ArrayList<Genre>>() {
                @Override
                public void onChanged(ArrayList<Genre> genres) {
                    if (genres != null){
                        adapterMovie.setListGenre(genres);
                    }
                }
            });
        }else {
            searchViewModel.setTvs();
            searchViewModel.setGenres();
            adapterTv = new TvAdapter();
            adapterTv.notifyDataSetChanged();
            rvMovie.setAdapter(adapterTv);
            searchViewModel.getTvs().observe(this, new Observer<ArrayList<TvShow>>() {
                @Override
                public void onChanged(ArrayList<TvShow> tvs) {
                    if (tvs != null) {
                        adapterTv.setListMovie(tvs);
                        showLoading(false);
                    }else if(adapterMovie.getListMovie().isEmpty()) {
                        showError();
                    }
                }
            });

            adapterTv.setOnItemClickCallback(new TvAdapter.OnItemClickCallback() {
                @Override
                public void onItemClicked(TvShow tv) {
                    showDetailTv(tv);
                }
            });

            searchViewModel.getgenres().observe(this, new Observer<ArrayList<Genre>>() {
                @Override
                public void onChanged(ArrayList<Genre> genres) {
                    if (genres != null){
                        adapterTv.setListGenre(genres);
                    }
                }
            });
        }

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

    private void showDetailTv(TvShow tvShow){
        TvShow object = new TvShow();
        object.setId(tvShow.getId());
        object.setTitle(tvShow.getTitle());
        object.setPoster(tvShow.getPoster());
        object.setRelease(tvShow.getRelease());
        object.setGenre(tvShow.getGenre());
        object.setOverview(tvShow.getOverview());
        object.setRating(tvShow.getRating());
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TV, object);
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
