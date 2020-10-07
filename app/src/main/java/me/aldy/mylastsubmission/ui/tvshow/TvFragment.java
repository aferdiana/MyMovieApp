package me.aldy.mylastsubmission.ui.tvshow;

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
import me.aldy.mylastsubmission.adapter.TvAdapter;
import me.aldy.mylastsubmission.model.Genre;
import me.aldy.mylastsubmission.model.TvShow;

public class TvFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private TvAdapter adapter;

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

        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        TvViewModel tvViewModel;
        tvViewModel = new ViewModelProvider(this).get(TvViewModel.class);
        tvViewModel.setMovies();
        tvViewModel.setGenres();
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
        tvViewModel.getMovies().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> tvShow) {
                if (tvShow != null) {
                    adapter.setListMovie(tvShow);
                    showLoading(false);
                }else if(adapter.getListMovie().isEmpty()) {
                    showError();
                }
            }
        });

        tvViewModel.getgenres().observe(this, new Observer<ArrayList<Genre>>() {
            @Override
            public void onChanged(ArrayList<Genre> genres) {
                if (genres != null){
                    adapter.setListGenre(genres);
                }
            }
        });


        adapter.setOnItemClickCallback(new TvAdapter.OnItemClickCallback() {
           @Override
           public void onItemClicked(TvShow tv) {
               showDetailTv(tv);
           }
       });

        showLoading(true);
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
