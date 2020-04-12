package com.example.booklister;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ListingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book> > {
    private TextView mEmptyStateTextView;
    private static final int BOOK_LOADER_ID = 1;
    public static final String LOG_TAG = ListingActivity.class.getName();
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);


        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new BookAdapter(ListingActivity.this, new ArrayList<Book>());
        listView.setAdapter(adapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID,null,this);
        }
        else{
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }


    @NonNull
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String search = getIntent().getStringExtra("text");
        String BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=" + search + "&maxResults=40";
        return new BookLoader(ListingActivity.this, BOOK_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        mEmptyStateTextView.setText(R.string.no_books);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        adapter.clear();
        if(books != null){
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Book>> loader) {
        adapter.clear();
    }
}
