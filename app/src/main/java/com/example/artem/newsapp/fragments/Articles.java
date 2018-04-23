package com.example.artem.newsapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.artem.newsapp.Downloader;
import com.example.artem.newsapp.MainActivity;
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.AppDataBase;
import com.example.artem.newsapp.dataBase.Article;
import com.example.artem.newsapp.dialogFragment.MyDialogFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.reactivex.functions.Consumer;

public class Articles extends Fragment {

    private static final String RESOURCE = "https://lifehacker.com/rss";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Article> listOfArticles;
    public AdapterOfArticles mAdapter;
    private MyDialogFragment dialogFragment;
    private ViewModel viewModel;
    private boolean bookmarkIsConfirmed;
    private int mPosition;
    private Downloader downloader;
    private AppDataBase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.articles_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        viewModel = new ViewModel(getActivity().getApplication());
        downloader = new Downloader(getActivity());
        db = AppDataBase.getDatabase(getActivity());
        new GetListOfArticlesAsyncTask().execute();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                new GetListOfArticlesAsyncTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private class GetListOfArticlesAsyncTask extends AsyncTask<Void,Void,Void>{
        List<Article> articles = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            articles = downloader.getDataFromDataBase(RESOURCE);
            Collections.sort(articles, new ArticlesDateComparator());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listOfArticles = articles;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new AdapterOfArticles(getActivity(), listOfArticles);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new AdapterOfArticles.OnItemClickListener() {

                @Override
                public void onItemClick(final int position) {
                    mPosition = position;
                    String urlForImage = listOfArticles.get(position).getThumbnailUrl();
                    String textOfTitle = listOfArticles.get(position).getTitle();
                    final String link = listOfArticles.get(position).getLink();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    dialogFragment = new MyDialogFragment();
                    dialogFragment.setImageUrl(urlForImage);
                    dialogFragment.setTitleText(textOfTitle);
                    dialogFragment.setTextOfLink(link);
                    dialogFragment.show(fm,"hi");
                    ((MainActivity) getActivity())
                            .bus()
                            .toObservable()
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    boolean finalResult;
                                    if(aBoolean){
                                        setResultThere(aBoolean);
                                        mAdapter.notifyDataSetChanged();
                                        //Log.i(TAG,"is supposed to be true" + aBoolean + " position" + position);
                                    }
                                    else{
                                        setResultThere(aBoolean);
                                        mAdapter.notifyDataSetChanged();
                                        //Log.i(TAG,"is supposed to be false" + aBoolean + " position" + position);
                                    }
                                }


                            });
                    }
            });
        }
    }

    public void setResultThere(boolean isTrue){
        listOfArticles.get(mPosition).setIsBookmarked(isTrue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.articleDao().update(listOfArticles.get(mPosition));
            }
        }).start();
    }
}