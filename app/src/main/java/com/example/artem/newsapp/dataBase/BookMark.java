package com.example.artem.newsapp.dataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bookmarksRepository")
public class BookMark {
    @PrimaryKey(autoGenerate = true)
    private int pid;
    private String titleOfBookMarkedPage;
    private String dateOfBookMarkedPage;
    private String linkOfBookMarkedPage;
    private String thumbnailUrlOfBookMarkedPage;

    public int getPid(){
        return pid;
    }

    public void setPid(int pid){
        this.pid = pid;
    }

    public void setTitleOfBookMarkedPage(String titleOfBookMarkedPage){
        this.titleOfBookMarkedPage = titleOfBookMarkedPage;
    }

    public String getTitleOfBookMarkedPage(){
        return titleOfBookMarkedPage;
    }

    public void setDateOfBookMarkedPage(String data){
        this.dateOfBookMarkedPage = data;
    }

    public String getDateOfBookMarkedPage(){
        return dateOfBookMarkedPage;
    }


    public void setLinkOfBookMarkedPage(String linkOfBookMarkedPage){
        this.linkOfBookMarkedPage = linkOfBookMarkedPage;
    }

    public String getLinkOfBookMarkedPage(){
        return linkOfBookMarkedPage;
    }


    public void setThumbnailUrlOfBookMarkedPage(String thumbnailUrlOfBookMarkedPage){
        this.thumbnailUrlOfBookMarkedPage = thumbnailUrlOfBookMarkedPage;
    }

    public String getThumbnailUrlOfBookMarkedPage(){
        return thumbnailUrlOfBookMarkedPage;
    }
}
