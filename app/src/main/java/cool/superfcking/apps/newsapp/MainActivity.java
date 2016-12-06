package cool.superfcking.apps.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private final int LOADER_ID = 1;
    private final String GUARDIAN_BASE_URL = "https://content.guardianapis.com/search";
    private final String API_KEY = "test";
    private final String AUTHOR_VALUE = "all";

    private NewsAdapter mAdapter;
    private TextView mEmptyView;
    private ProgressBar mProgressBar;
    private EditText mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.newsProgress);
        mProgressBar.setVisibility(View.GONE);

        mEmptyView = (TextView) findViewById(R.id.emptyList);

        ListView newsListView = (ListView) findViewById(R.id.newsList);
        newsListView.setEmptyView(mEmptyView);

        mAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentArticle = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentArticle.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

        mSearchQuery = (EditText) findViewById(R.id.searchText);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchQuery.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        mProgressBar.setVisibility(View.VISIBLE);

        String query = mSearchQuery.getText().toString().trim();

        Uri baseUri = Uri.parse(GUARDIAN_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("api-key", API_KEY);
        uriBuilder.appendQueryParameter("show-references", AUTHOR_VALUE);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> books) {
        mAdapter.clear();
        mProgressBar.setVisibility(View.GONE);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            mEmptyView.setText(R.string.error_no_news);
        } else {
            mEmptyView.setText(R.string.error_no_internet);
        }

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
        mProgressBar.setVisibility(View.GONE);
    }

}
