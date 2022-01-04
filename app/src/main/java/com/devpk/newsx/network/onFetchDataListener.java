package com.devpk.newsx.network;

import com.devpk.newsx.model.Article;

import java.util.ArrayList;

public interface onFetchDataListener<TopHeadlines> {

    void onFetchData(ArrayList<Article> list, String message);

    void onError(String message);
}
