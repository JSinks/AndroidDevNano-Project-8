package cool.superfcking.apps.newsapp;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by jsinclair on 5/12/16.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /** Tag for the log messages */
    public static final String LOG_TAG = Utils.class.getSimpleName();

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> articles = Utils.fetchNews(mUrl);
        return articles;
    }
}
