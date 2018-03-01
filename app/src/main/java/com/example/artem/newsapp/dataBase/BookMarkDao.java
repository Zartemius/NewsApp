package com.example.artem.newsapp.dataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface BookMarkDao {
    @Query("SELECT * FROM bookmarksRepository")
    LiveData<List<BookMark>> getAll();

    @Query("SELECT * FROM bookmarksRepository where titleOfBookMarkedPage LIKE :title")
    BookMark findByTitle(String title);

    @Query("DELETE FROM bookmarksRepository")
    public void clearListOfArticles();

    @Insert
    public void addBookMark(BookMark bookMark);

    @Delete
    public void deleteBookMark (BookMark bookMark);
}
