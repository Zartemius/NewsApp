package com.example.artem.newsapp.fragments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;
import com.example.artem.newsapp.dataBase.AppDataBase;
import com.example.artem.newsapp.dataBase.Article;
import com.example.artem.newsapp.dataBase.BookMark;
import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {
    private AppDataBase appDataBase;
    private final LiveData<List<BookMark>> listOfBookMarks;

    public ViewModel(Application application){
        super(application);
        appDataBase = AppDataBase.getDatabase(this.getApplication());
        listOfBookMarks = appDataBase.bookMarkDao().getAll();
    }

    public LiveData<List<BookMark>> getListOfBookMarks(){
        return listOfBookMarks;
    }



    public void findAndDeleteBookmark(String title){
        new findAndDeleteBookMarkAsyncTask(appDataBase, title).execute();
    }



    public void addBookMarkInAList(BookMark bookMark){
        new AddBookMarkAsynkTask(appDataBase,bookMark).execute();
    }


    private static class AddBookMarkAsynkTask extends AsyncTask<Void,Void,Void>{

        private AppDataBase db;
        private BookMark bookMark;

        AddBookMarkAsynkTask(AppDataBase appDataBase, BookMark bookMark){
            db = appDataBase;
            this.bookMark = bookMark;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.bookMarkDao().addBookMark(bookMark);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class findAndDeleteBookMarkAsyncTask extends AsyncTask<Void,Void,Void>{
        private AppDataBase db;
        private String title;
        private boolean itemFound = false;

        findAndDeleteBookMarkAsyncTask(AppDataBase appDataBase, String title){
            db = appDataBase;
            this.title = title;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BookMark bookMark = db.bookMarkDao().findByTitle(title);
            String matchedTitle = bookMark.getTitleOfBookMarkedPage();

            if(matchedTitle.equals(title)){
                db.bookMarkDao().deleteBookMark(bookMark);
                itemFound = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(itemFound){
                Toast toast = Toast.makeText(getApplication(), " Bookmark was deleted",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }

            super.onPostExecute(aVoid);
        }
    }
}
