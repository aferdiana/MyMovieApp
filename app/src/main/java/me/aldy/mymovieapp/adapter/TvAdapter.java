package me.aldy.mymovieapp.adapter;

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

import me.aldy.mymovieapp.R;
import me.aldy.mymovieapp.model.Genre;
import me.aldy.mymovieapp.model.TvShow;


public class TvAdapter extends RecyclerView.Adapter<TvAdapter.CardViewViewHolder> {
    private ArrayList<TvShow> listTvShow = new ArrayList<>();
    private ArrayList<Genre> listGenre = new ArrayList<>();
    private TvAdapter.OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback{
        void onItemClicked(TvShow tv);
    }

    public void setOnItemClickCallback(TvAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public ArrayList<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(ArrayList<TvShow> item) {
        listTvShow.clear();
        listTvShow.addAll(item);
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
    public TvAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TvAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.CardViewViewHolder holder, final int position) {
        final TvShow tv = listTvShow.get(position);
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
                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
//                Toast.makeText(holder.itemView.getContext(), "test", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getGenres(List<Integer> genreIds) {
        List<String> tvGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            for (Genre genre : listGenre) {
                if (genre.getId() == genreId) {
                    tvGenres.add(genre.getName());
                    break;
                }
            }
        }

        return TextUtils.join(", ", tvGenres);
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
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
