package com.example.artem.newsapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.artem.newsapp.R;
import com.example.artem.newsapp.dataBase.BookMark;
import java.util.ArrayList;
import java.util.List;

public class Bookmarks extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterOfBookmarks mAdapter;
    private ViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmarks_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.bookmarks_fragment__recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AdapterOfBookmarks(getActivity(), new ArrayList<BookMark>());
        mRecyclerView.setAdapter(mAdapter);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getListOfBookMarks().observe(Bookmarks.this, new Observer<List<BookMark>>() {
            @Override
            public void onChanged(@Nullable List<BookMark> bookMarks) {
                mAdapter.addItems(bookMarks);
            }
        });
        return view;
    }
}
