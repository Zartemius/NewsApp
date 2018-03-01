package com.example.artem.newsapp.fragments;

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
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.Article;
import com.example.artem.newsapp.dialogFragment.MyDialogFragment;
import java.util.List;

public class Articles extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterOfArticles mAdapter;
    private List<Article> listOfArticles;
    private ViewModel viewModel;
    private MyDialogFragment dialogFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.articles_fragment, container, false);

        viewModel = new ViewModel(getActivity().getApplication());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                Downloader downloader = new Downloader(getActivity(), mSwipeRefreshLayout);
                downloader.execute();
                listOfArticles = viewModel.getListOfArticles();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new AdapterOfArticles(getActivity(), listOfArticles);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new AdapterOfArticles.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String urlForImage = listOfArticles.get(position).getThumbnailUrl();
                        String textOfTitle = listOfArticles.get(position).getTitle();
                        String link = listOfArticles.get(position).getLink();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        dialogFragment = new MyDialogFragment();
                        dialogFragment.setImageUrl(urlForImage);
                        dialogFragment.setTitleText(textOfTitle);
                        dialogFragment.setTextOfLink(link);
                        dialogFragment.show(fm,"hi");
                    }
                });
            }
        });
        return view;
    }
}
