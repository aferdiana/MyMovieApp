package com.example.mymoviecatalogue.movie;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymoviecatalogue.DetailActivity;
import com.example.mymoviecatalogue.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();

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
        holder.ratingBar.setRating((movie.getRating() / 2) + 1f);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(120, 220))
                .into(holder.img_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie object = new Movie();
                object.setId(movie.getId());
                object.setTitle(movie.getTitle());
                object.setPoster(movie.getPoster());
                object.setRelease_date(movie.getRelease_date());
                object.setRating(movie.getRating());
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_overview;
        ImageView img_poster;
        RatingBar ratingBar;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            img_poster = itemView.findViewById(R.id.imageview_poster);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }
    }
}
