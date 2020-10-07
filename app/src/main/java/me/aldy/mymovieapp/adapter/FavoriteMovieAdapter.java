package me.aldy.mymovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import me.aldy.mymovieapp.R;
import me.aldy.mymovieapp.db.FavDatabase;
import me.aldy.mymovieapp.model.Movie;
import me.aldy.mymovieapp.model.TvShow;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.CardViewViewHolder> {
    private ArrayList<Movie> listMovie;
    private FavDatabase db;
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback{
        void onItemClicked(Movie movie);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public FavoriteMovieAdapter(ArrayList<Movie> listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
        db = Room.databaseBuilder(
                context.getApplicationContext(),
                FavDatabase.class, "dbFav").allowMainThreadQueries().build();
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> item) {
        listMovie.clear();
        listMovie.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        final Movie movie = listMovie.get(position);
        holder.tv_title.setText(movie.getTitle());
        holder.tv_overview.setText(movie.getOverview());
        holder.tv_genre.setText(holder.itemView.getContext().getString(R.string.label_genre, movie.getGenre()));
        holder.ratingBar.setRating((movie.getRating() / 2));
        holder.tv_rating.setText(String.valueOf(movie.getRating()));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(120, 220))
                .into(holder.img_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_overview, tv_genre, tv_rating;
        ImageView img_poster;
        RatingBar ratingBar;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            tv_genre = itemView.findViewById(R.id.tv_genre);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            img_poster = itemView.findViewById(R.id.imageview_poster);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }
    }
}
