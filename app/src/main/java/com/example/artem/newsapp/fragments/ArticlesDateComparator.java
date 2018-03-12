package com.example.artem.newsapp.fragments;


import com.example.artem.newsapp.dataBase.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ArticlesDateComparator implements Comparator<Article> {

    @Override
    public int compare(Article date1, Article date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        try {
            Date d1 = sdf.parse(date1.getDate());
            Date d2 = sdf.parse(date2.getDate());
            return d2.compareTo(d1);

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
