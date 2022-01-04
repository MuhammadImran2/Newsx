package com.devpk.newsx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devpk.newsx.OnSelected;
import com.devpk.newsx.R;
import com.devpk.newsx.model.Article;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Article> articles;
    private OnSelected listener;

    public CustomAdapter(Context context, ArrayList<Article> articles, OnSelected listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.headlines_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Article model = articles.get(position);
        holder.text_title.setText(model.getTitle());
        holder.text_source.setText(model.getSource().getName());

        if (model.getUrlToImage() != null) {
            Glide.with(context).load(model.getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNewsClicked(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView text_title, text_source;
        private ImageView img_headline;
        CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            text_source = itemView.findViewById(R.id.text_source);
            text_title = itemView.findViewById(R.id.text_title);
            img_headline = itemView.findViewById(R.id.img_headline);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
