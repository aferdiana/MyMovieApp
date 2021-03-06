package me.aldy.mylastsubmission.ui.tvshow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Locale;

import me.aldy.mylastsubmission.BuildConfig;
import me.aldy.mylastsubmission.api.TmdbApi;
import me.aldy.mylastsubmission.model.Genre;
import me.aldy.mylastsubmission.model.GenreResponse;
import me.aldy.mylastsubmission.model.TvShow;
import me.aldy.mylastsubmission.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvViewModel extends ViewModel {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = BuildConfig.ApiKey;
    private static Retrofit retrofit = null;
    private MutableLiveData<ArrayList<TvShow>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Genre>> listGenres = new MutableLiveData<>();

    public TvViewModel() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private String getLanguange(String language) {
        if (language.equals("in_ID")) {
            return "id-ID";
        } else {
            return "en-US";
        }
    }

    public void setMovies() {
        String LANGUAGE = Locale.getDefault().toString();
        TmdbApi con = retrofit.create(TmdbApi.class);
        Call<TvShowResponse> call = con.getTvs(API_KEY, getLanguange(LANGUAGE));
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse mr = response.body();
                listMovies.postValue(mr.getTvShows());
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                listMovies.postValue(null);
            }
        });
    }

    public void setGenres() {
        String LANGUAGE = Locale.getDefault().toString();
        TmdbApi con = retrofit.create(TmdbApi.class);
        Call<GenreResponse> call = con.getGenres(API_KEY, getLanguange(LANGUAGE));
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                GenreResponse gr = response.body();
                listGenres.postValue(gr.getGenres());
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                listGenres.postValue(null);
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<Genre>> getgenres() {
        return listGenres;
    }


}
