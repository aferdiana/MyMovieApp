package me.aldy.mylastsubmission.adapter;

import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.model.Genre;
import me.aldy.mylastsubmission.model.TvShow;
import me.aldy.mylastsubmission.model.TvShow;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.CardViewViewHolder> {
    private ArrayList<TvShow> listTv = new ArrayList<>();
    private ArrayList<Genre> listGenre = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback{
        void onItemClicked(TvShow tv);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public ArrayList<TvShow> getListMovie() {
        return listTv;
    }

    public void setListMovie(ArrayList<TvShow> item) {
        listTv.clear();
        listTv.addAll(item);
        notifyDataSetChanged();
    }

    public void setListGenre(ArrayList<Genre> item) {
        listGenre.clear();
        listGenre.addAll(item);
        notifyDataSetChanged();
    }

    public ArrayList<Genre> getListGenre(){
        return listGenre;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        final TvShow tv = listTv.get(position);
        tv.setGenre(getGenres(tv.getGenreId()));
        holder.tv_title.setText(tv.getTitle());
        holder.tv_overview.setText(tv.getOverview());
        holder.tv_genre.setText(holder.itemView.getContext().getString(R.string.label_genre, tv.getGenre()));
        holder.ratingBar.setRating((tv.getRating() / 2));
        holder.tv_rating.setText(String.valueOf(tv.getRating()));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/"+tv.getPoster())
                .centerCrop()
                .apply(new RequestOptions().override(120, 220))
                .into(holder.img_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listTv.get(holder.getAdapterPosition()));
            }
        });
    }

    private String getGenres(List<Integer> genreIds) {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            for (Genre genre : listGenre) {
                if (genre.getId() == genreId) {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }

        return TextUtils.join(", ", movieGenres);
    }

    @Override
    public int getItemCount() {
        return listTv.size();
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

