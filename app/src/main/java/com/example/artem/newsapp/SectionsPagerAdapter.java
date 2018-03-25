package com.example.artem.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.artem.newsapp.fragments.Articles;
import com.example.artem.newsapp.fragments.Bookmarks;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final String firstSection = "Articles";
    private final String secondSection= "Bookmarks";

    public SectionsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new Articles();
                break;
            case 1:
                fragment = new Bookmarks();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return firstSection;
        }
        if(position == 1){

            return secondSection;
        }
        return null;
    }
}

