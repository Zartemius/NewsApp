package com.example.artem.newsapp.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.Article;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AdapterOfArticles extends RecyclerView.Adapter<AdapterOfArticles.NewViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    private Context context;
    private List<Article> articles = new ArrayList();
    private OnItemClickListener mListener;

    public AdapterOfArticles(Context context, List<Article> articles){
        this.context = context;
        this.articles = articles;
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        ImageView image;

        public NewViewHolder(View viewItem, final OnItemClickListener listener) {
            super(viewItem);
            title = itemView.findViewById(R.id.item_view_holder__titleTxt);
            image = itemView.findViewById(R.id.item_view_holder__articleImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {
        Article currentItem = articles.get(position);

        String title = currentItem.getTitle();
        String thumbnailUrl = currentItem.getThumbnailUrl();
        holder.title.setText(title);
        Picasso.with(context)
                 .load(thumbnailUrl)
                 .into(holder.image);
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_holder,parent,false);
        NewViewHolder newViewHolder = new NewViewHolder(view,mListener);
        return newViewHolder;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
