package me.aldy.mymovieapp.ui.tv;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Locale;

import me.aldy.mymovieapp.api.TmbdApi;
import me.aldy.mymovieapp.model.Genre;
import me.aldy.mymovieapp.model.GenreResponse;
import me.aldy.mymovieapp.model.TvShow;
import me.aldy.mymovieapp.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvViewModel extends ViewModel {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "364cf500f0384b8c4a3a11add07166fa";
    private static Retrofit retrofit = null;
    private MutableLiveData<ArrayList<TvShow>> listTvs = new MutableLiveData<>();
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

    void setTvs() {
        String LANGUAGE = Locale.getDefault().toString();
        TmbdApi con = retrofit.create(TmbdApi.class);
        Call<TvShowResponse> call = con.getTvShows(API_KEY, getLanguange(LANGUAGE));
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tr = response.body();
                listTvs.postValue(tr.getTvShow());
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                listTvs.postValue(null);
            }
        });
    }

    void setGenres() {
        String LANGUAGE = Locale.getDefault().toString();
        TmbdApi con = retrofit.create(TmbdApi.class);
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

    LiveData<ArrayList<TvShow>> getTvShows() {
        return listTvs;
    }

    LiveData<ArrayList<Genre>> getgenres() {
        return listGenres;
    }
}