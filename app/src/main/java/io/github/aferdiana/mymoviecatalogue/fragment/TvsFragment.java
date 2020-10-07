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
public class TvsFragment extends Fragment {
    private RecyclerView rvTvs;
    private MovieAdapter adapter;
    private ArrayList<Movie> list;
    private String[] dataTitle, dataOverview, dataRating, dataDuration, dataGenre, dataRelease;
    private TypedArray dataPoster;

    public TvsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvs = view.findViewById(R.id.rv_tvs);
        rvTvs.setHasFixedSize(true);

        showRecyclerList();
    }

    private void showRecyclerList() {
        rvTvs.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter(getActivity());
        adapter.setListMovie(list);
        rvTvs.setAdapter(adapter);

        init();
        addItem();

    }

    private void init() {
        dataGenre = getResources().getStringArray(R.array.data_tv_show_genre);
        dataTitle = getResources().getStringArray(R.array.data_tv_show_title);
        dataPoster = getResources().obtainTypedArray(R.array.data_tv_show_poster);
        dataRating = getResources().getStringArray(R.array.data_tv_show_rating);
        dataRelease = getResources().getStringArray(R.array.data_tv_show_release);
        dataOverview = getResources().getStringArray(R.array.data_tv_show_overview);
        dataDuration = getResources().getStringArray(R.array.data_tv_show_runtime);
    }

    private void addItem() {
        list = new ArrayList<>();

        for (int i = 0; i < dataTitle.length; i++) {
            Movie tvshow = new Movie();
            tvshow.setGenre(dataGenre[i]);
            tvshow.setTitle(dataTitle[i]);
            tvshow.setDate(dataRelease[i]);
            tvshow.setRating(dataRating[i]);
            tvshow.setDuration(dataDuration[i]);
            tvshow.setOverview(dataOverview[i]);
            tvshow.setPoster(dataPoster.getResourceId(i, -1));
            list.add(tvshow);
        }
        adapter.setListMovie(list);
    }

}
