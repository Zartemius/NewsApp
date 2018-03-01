package com.example.artem.newsapp.dataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articlesRepository")
    List <Article> getAll();

    @Query("DELETE FROM articlesRepository")
    public void clearListOfArticles();

    @Insert
    public void addArticle(Article article);
}
