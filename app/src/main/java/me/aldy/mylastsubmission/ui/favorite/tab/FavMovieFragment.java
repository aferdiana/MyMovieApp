package me.aldy.mylastsubmission.ui.favorite.tab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.adapter.MovieFavAdapter;
import me.aldy.mylastsubmission.database.FavoriteHelper;
import me.aldy.mylastsubmission.model.Movie;

public class FavMovieFragment extends Fragment {
    private ArrayList<Movie> arrayList;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieFavAdapter adapter;
    private FavoriteHelper helper;
    private ImageView imgEmpty;
    private FavMovieViewModel favMovieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        adapter = new MovieFavAdapter();

        final View root = inflater.inflate(R.layout.fav_movie_fragment, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        rvMovie = root.findViewById(R.id.rv_movies);
        helper = FavoriteHelper.getInstance(getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favMovieViewModel = new ViewModelProvider(this).get(FavMovieViewModel.class);
        favMovieViewModel.setMovies();

        arrayList = favMovieViewModel.getMovies().getValue();

        adapter.setListMovie(arrayList);
        adapter.notifyDataSetChanged();

        rvMovie.setAdapter(adapter);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));


        favMovieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
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

        adapter.setOnItemClickCallback(new MovieFavAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                removeMovie(movie);
            }
        });

        showLoading(true);

        if (arrayList.isEmpty()) {
            showEmpty(view);
            favMovieViewModel.setMovies();
        }
    }


    private void removeMovie(final Movie movie) {
        final Movie object = new Movie();
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
                favMovieViewModel.setMovies();
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
