package me.aldy.mymovieapp.ui.tv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.aldy.mymovieapp.DetailActivity;
import me.aldy.mymovieapp.R;
import me.aldy.mymovieapp.adapter.TvAdapter;

import me.aldy.mymovieapp.model.Genre;
import me.aldy.mymovieapp.model.TvShow;

public class TvFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView rvTvShow;
    private TvAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tv, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        rvTvShow = root.findViewById(R.id.rv_tvs);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        TvViewModel tvViewModel;
        tvViewModel = new ViewModelProvider(this).get(TvViewModel.class);
        tvViewModel.setTvs();
        tvViewModel.setGenres();
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShow.setAdapter(adapter);
        tvViewModel.getTvShows().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> tvShows) {
                if (tvShows != null) {
                    adapter.setListTvShow(tvShows);
                    showLoading(false);
                }else if(adapter.getListTvShow().isEmpty()) {
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
                showDetailTvShow(tv);
            }
        });

        showLoading(true);
    }

    private void showDetailTvShow(TvShow movie){
        TvShow object = new TvShow();
        object.setId(movie.getId());
        object.setTitle(movie.getTitle());
        object.setPoster(movie.getPoster());
        object.setRelease(movie.getRelease());
        object.setGenre(movie.getGenre());
        object.setOverview(movie.getOverview());
        object.setRating(movie.getRating());
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