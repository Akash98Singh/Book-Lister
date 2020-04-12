package com.example.booklister;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book> > {
    private static final String LOG_TAG = ListingActivity.class.getName();
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        ArrayList<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
