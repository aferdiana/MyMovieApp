package me.aldy.mymovieapp.api;

import me.aldy.mymovieapp.model.GenreResponse;
import me.aldy.mymovieapp.model.MovieResponse;
import me.aldy.mymovieapp.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmbdApi {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TvShowResponse> getTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
