package com.example.artem.newsapp.dataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "articlesRepository")
public class Article {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    private String title;
    private String date;
    private String link;
    private String thumbnailUrl;
    private boolean isBookmarked;

    public int getPid(){
        return pid;
    }

    public void setPid(int pid){
        this.pid = pid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDate(String data){
        this.date = data;
    }

    public String getDate(){
        return date;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public void setIsBookmarked(boolean isBookmarked){
        this.isBookmarked = isBookmarked;
    }

    public boolean getIsBookmarked(){
        return isBookmarked;
    }

    @Override
    public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        return link.equals(article.link);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + link.hashCode();
        return result;
    }
}
