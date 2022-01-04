package com.devpk.newsx.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devpk.newsx.OnSelected;
import com.devpk.newsx.R;
import com.devpk.newsx.adapter.CustomAdapter;
import com.devpk.newsx.model.Article;
import com.devpk.newsx.model.TopHeadlines;
import com.devpk.newsx.network.RequestManager;
import com.devpk.newsx.network.onFetchDataListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnSelected, View.OnClickListener {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ProgressDialog dialog;
    private Button b1, b2, b3, b4, b5, b6, b7;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_View);
        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn7);
        b7.setOnClickListener(this);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles.....");
        dialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        RequestManager manager = new RequestManager(getApplicationContext());
        manager.getResponse(listener, "general", null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(getApplicationContext());
                manager.getResponse(listener, "general", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final onFetchDataListener<TopHeadlines> listener = new onFetchDataListener<TopHeadlines>() {
        @Override
        public void onFetchData(ArrayList<Article> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No Data Found ", Toast.LENGTH_SHORT).show();
            } else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getApplicationContext(), "Error !!!!!!!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(ArrayList<Article> list) {
        adapter = new CustomAdapter(this, list, this::onNewsClicked);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNewsClicked(Article article) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("data", article);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();

        dialog.setTitle("Fetching news articles of " + category);
        dialog.show();

        RequestManager manager = new RequestManager(getApplicationContext());
        manager.getResponse(listener, category, null);

    }
}