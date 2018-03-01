package com.example.artem.newsapp.dataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Article.class, BookMark.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;
    private static final Object LOCK = new Object();

    public synchronized static AppDataBase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (LOCK) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "dataBase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract ArticleDao articleDao();
    public abstract BookMarkDao bookMarkDao();
}
