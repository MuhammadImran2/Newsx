package com.devpk.newsx.network;

import android.content.Context;
import android.widget.Toast;

import com.devpk.newsx.R;
import com.devpk.newsx.model.TopHeadlines;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;

    public RequestManager(Context context) {
        this.context = context;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getResponse(onFetchDataListener listener, String category, String query) {
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<TopHeadlines> call = callNewsApi
                .headlines(context.getString(R.string.apiKey), "us", category, query);
        call.enqueue(new Callback<TopHeadlines>() {
            @Override
            public void onResponse(Call<TopHeadlines> call, Response<TopHeadlines> response) {
                if (response.isSuccessful()) {
                    listener.onFetchData(response.body().getArticles(), response.message());
                } else {
                    Toast.makeText(context.getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopHeadlines> call, Throwable t) {
                listener.onError(t.getMessage());
                Toast.makeText(context.getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public interface CallNewsApi {

        @GET("top-headlines")
        Call<TopHeadlines> headlines(
                @Query("apiKey") String apiKey,
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String q
        );
    }
}
