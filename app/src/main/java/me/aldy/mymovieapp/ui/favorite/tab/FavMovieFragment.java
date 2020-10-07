package me.aldy.mymovieapp.ui.favorite.tab;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.aldy.mymovieapp.R;
import me.aldy.mymovieapp.adapter.FavoriteMovieAdapter;
import me.aldy.mymovieapp.db.FavDatabase;
import me.aldy.mymovieapp.model.Movie;

public class FavMovieFragment extends Fragment {
    private ImageView imgEmpty;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private FavoriteMovieAdapter adapter;
    private FavDatabase db;
    private ArrayList<Movie> listFav = new ArrayList<>();

    public FavMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        rvMovie = root.findViewById(R.id.rv_movies);
        db = Room.databaseBuilder(
                getActivity().getApplicationContext(),
                FavDatabase.class, "dbFav").allowMainThreadQueries().build();

        listFav.addAll(db.favDao().getAllMovie());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FavoriteMovieAdapter(listFav, getActivity());
        adapter.notifyDataSetChanged();
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                delMovie(movie);
            }
        });

      if (listFav.isEmpty()) {
            imgEmpty =  view.findViewById(R.id.img_empty);
            imgEmpty.setVisibility(View.VISIBLE);
            Glide.with(view)
                    .load(R.drawable.empty_data_set)
                    .centerCrop()
                    .into(imgEmpty);
        }

    }

    private void delMovie(final Movie movie){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getString(R.string.confirm_remove));
        dialog.setPositiveButton(getActivity().getString(R.string.confirm_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.favDao().removeMovie(movie);
                        listFav.remove(movie);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i, listFav.size());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), getActivity().getString(R.string.removed, movie.getTitle()), Toast.LENGTH_SHORT).show();
                        if (listFav.isEmpty()) {
                            imgEmpty =  getView().findViewById(R.id.img_empty);
                            imgEmpty.setVisibility(View.VISIBLE);
                            Glide.with(getView())
                                    .load(R.drawable.empty_data_set)
                                    .centerCrop()
                                    .into(imgEmpty);
                        }
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

}
