package com.example.mymoviecatalogue.tv;

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

public class TVsViewModel extends ViewModel {
    private static final String API_KEY = "364cf500f0384b8c4a3a11add07166fa";
    private MutableLiveData<ArrayList<Tv>> listTvs = new MutableLiveData<>();

    LiveData<ArrayList<Tv>> getTvs() {
        return listTvs;
    }

    void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tv> listTvItem = new ArrayList<>();
        String MY_LANG = Locale.getDefault().toString();
        if (MY_LANG.equals("in_ID")) {
            MY_LANG = "id-ID";
        } else {
            MY_LANG = "en-US";
        }

        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=" + MY_LANG;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject responseObj = new JSONObject(res);
                    JSONArray list = responseObj.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieObj = list.getJSONObject(i);
                        Tv movie = new Tv(movieObj);
                        listTvItem.add(movie);
                    }
                    listTvs.postValue(listTvItem);
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
}
