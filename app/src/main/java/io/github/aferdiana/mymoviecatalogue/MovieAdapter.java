package io.github.aferdiana.mymoviecatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);

        Movie movie = (Movie) getItem(position);
        viewHolder.bind(movie);

        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle, tvOverview;
        private ImageView imgPoster;

        ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tv_title);
            tvOverview = view.findViewById(R.id.tv_overview);
            imgPoster = view.findViewById(R.id.img_poster);
        }

        void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPoster()).into(imgPoster);
        }
    }
}
