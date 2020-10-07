package me.aldy.mylastsubmission.ui.favorite.tab;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.adapter.TvFavAdapter;
import me.aldy.mylastsubmission.database.FavoriteHelper;
import me.aldy.mylastsubmission.model.TvShow;

public class FavTvFragment extends Fragment {
    private ArrayList<TvShow> arrayList;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private TvFavAdapter adapter;
    private FavoriteHelper helper;
    private ImageView imgEmpty;
    private FavTvViewModel favTvViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        adapter = new TvFavAdapter();

        final View root = inflater.inflate(R.layout.fav_tv_fragment, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        rvMovie = root.findViewById(R.id.rv_movies);
        helper = FavoriteHelper.getInstance(getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favTvViewModel = new ViewModelProvider(this).get(FavTvViewModel.class);
        favTvViewModel.setTvShows();

        arrayList = favTvViewModel.getTvShows().getValue();

        adapter.setListMovie(arrayList);
        adapter.notifyDataSetChanged();

        rvMovie.setAdapter(adapter);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));


        favTvViewModel.getTvShows().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> movies) {
                if (movies.isEmpty()) {
                    showEmpty(view);
                } else {
                    arrayList = movies;
                    adapter.setListMovie(arrayList);
                    adapter.notifyDataSetChanged();
                    showLoading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new TvFavAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow tv) {
                removeMovie(tv);
            }
        });
        showLoading(true);
        if (arrayList.isEmpty()) {
            showEmpty(view);
            favTvViewModel.setTvShows();
        }
    }


    private void removeMovie(final TvShow movie) {
        final TvShow object = new TvShow();
        object.setId(movie.getId());
        object.setTitle(movie.getTitle());
        object.setOverview(movie.getTitle());
        object.setRating(movie.getRating());
        object.setRelease(movie.getRelease());
        object.setPoster(movie.getPoster());

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getString(R.string.confirm_remove));
        dialog.setPositiveButton(getActivity().getString(R.string.confirm_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helper.open();
                helper.delete(String.valueOf(object.getId()));
                helper.close();
                Toast.makeText(getActivity(), getActivity().getString(R.string.removed, movie.getTitle()), Toast.LENGTH_SHORT).show();
                favTvViewModel.setTvShows();
                arrayList.remove(object);
                adapter.setListMovie(arrayList);
                adapter.notifyDataSetChanged();
            }
        });

        dialog.setNegativeButton(getActivity().getString(R.string.confirm_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.create();
        dialog.show();
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showEmpty(View view) {
        imgEmpty = view.findViewById(R.id.img_empty);
        imgEmpty.setVisibility(View.VISIBLE);
        Glide.with(view)
                .load(R.drawable.empty_data_set)
                .centerCrop()
                .into(imgEmpty);
        showLoading(false);
    }
}
