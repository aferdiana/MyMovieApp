package io.github.aferdiana.mymoviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.aferdiana.mymoviecatalogue.DetailActivity;
import io.github.aferdiana.mymoviecatalogue.R;
import io.github.aferdiana.mymoviecatalogue.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public MovieAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new CategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.CategoryViewHolder holder, final int position) {
        holder.tvTitle.setText(getListMovie().get(position).getTitle());
        holder.tvOverview.setText(getListMovie().get(position).getOverview());
        holder.imgPoster.setContentDescription(getListMovie().get(position).getTitle());
        Picasso.get()
                .load(getListMovie().get(position).getPoster())
                .resize(100, 150)
                .centerCrop()
                .into(holder.imgPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = new Movie();
                movie.setDate(getListMovie().get(position).getDate());
                movie.setGenre(getListMovie().get(position).getGenre());
                movie.setTitle(getListMovie().get(position).getTitle());
                movie.setRating(getListMovie().get(position).getRating());
                movie.setPoster(getListMovie().get(position).getPoster());
                movie.setDuration(getListMovie().get(position).getDuration());
                movie.setOverview(getListMovie().get(position).getOverview());

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview;
        ImageView imgPoster;

        CategoryViewHolder(View item) {
            super(item);
            tvTitle = item.findViewById(R.id.tv_title);
            tvOverview = item.findViewById(R.id.tv_overview);
            imgPoster = item.findViewById(R.id.img_poster);
        }
    }
}
