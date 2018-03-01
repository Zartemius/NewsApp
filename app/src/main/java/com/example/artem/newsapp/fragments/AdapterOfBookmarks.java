package com.example.artem.newsapp.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.BookMark;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AdapterOfBookmarks extends RecyclerView.Adapter <AdapterOfBookmarks.NewViewHolder>{

    private Context context;
    private List<BookMark> bookMarks = new ArrayList<>();

    public AdapterOfBookmarks(Context context, List<BookMark> list){
        this.context = context;
        this.bookMarks = list;
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public NewViewHolder(View viewItem) {
            super(viewItem);
            title = itemView.findViewById(R.id.item_view_holder__titleTxt);
            image = itemView.findViewById(R.id.item_view_holder__articleImage);
        }
    }

    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {
        BookMark currentItem = bookMarks.get(position);

        String title = currentItem.getTitleOfBookMarkedPage();
        String thumbnailUrl = currentItem.getThumbnailUrlOfBookMarkedPage();
        holder.title.setText(title);
        Picasso.with(context)
                .load(thumbnailUrl)
                .into(holder.image);
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_holder,parent,false);
        NewViewHolder newViewHolder = new NewViewHolder(view);
        return newViewHolder;
    }

    @Override
    public int getItemCount() {
        return bookMarks.size();
    }

    public void addItems(List<BookMark> listOfBookmarks){
        bookMarks = listOfBookmarks;
        notifyDataSetChanged();
    }
}
