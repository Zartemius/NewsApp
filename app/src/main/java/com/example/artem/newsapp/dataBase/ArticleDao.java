package com.example.artem.newsapp.dataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articlesRepository")
    List <Article> getAll();

    @Query("DELETE FROM articlesRepository")
    public void clearListOfArticles();

    @Query("SELECT * FROM articlesRepository where title LIKE :title")
    Article findByTitle(String title);

    @Update
    void update(Article... articles);

    @Delete
    public void deleteArticle (Article article);

    @Insert
    public void addArticle(Article article);
}
