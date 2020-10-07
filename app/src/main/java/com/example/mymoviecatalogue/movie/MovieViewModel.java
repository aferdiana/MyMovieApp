package com.example.mymoviecatalogue.movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "364cf500f0384b8c4a3a11add07166fa";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMoviesItem = new ArrayList<>();
        String MY_LANG = Locale.getDefault().toString();
        if (MY_LANG.equals("in_ID")) {
            MY_LANG = "id-ID";
        } else {
            MY_LANG = "en-US";
        }

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + MY_LANG;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject responseObj = new JSONObject(res);
                    JSONArray list = responseObj.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieObj = list.getJSONObject(i);
                        Movie movie = new Movie(movieObj);
                        listMoviesItem.add(movie);
                    }
                    listMovies.postValue(listMoviesItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
