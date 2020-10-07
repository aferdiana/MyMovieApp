package me.aldy.mylastsubmission.api;

import me.aldy.mylastsubmission.model.GenreResponse;
import me.aldy.mylastsubmission.model.MovieResponse;
import me.aldy.mylastsubmission.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApi {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TvShowResponse> getTvs(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/movie")
    Call<MovieResponse> releaseMovies(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String dateGte,
            @Query("primary_release_date.lte") String dateLte
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String title
    );

    @GET("search/tv")
    Call<TvShowResponse> searchTvs(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String title
    );

}
