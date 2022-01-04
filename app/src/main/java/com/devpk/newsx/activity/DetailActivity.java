package com.devpk.newsx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devpk.newsx.R;
import com.devpk.newsx.model.Article;

public class DetailActivity extends AppCompatActivity {

    private Article article;
    private TextView textTitle, textAuthor, textTime, textDetail, textContent;
    private ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //View
        textTitle = findViewById(R.id.textDetailTitle);
        textAuthor = findViewById(R.id.text_detail_author);
        textTime = findViewById(R.id.time);
        textDetail = findViewById(R.id.text_detail_detail);
        textContent = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail);
        //GetData
        article = (Article) getIntent().getSerializableExtra("data");
        //SetData
        textTitle.setText(article.getTitle());
        textAuthor.setText(article.getAuthor());
        textTime.setText(article.getPublishedAt().toString());
        textDetail.setText(article.getDescription());
        textContent.setText(article.getContent());
        Glide.with(this).load(article.getUrlToImage()).into(img_news);

    }
}