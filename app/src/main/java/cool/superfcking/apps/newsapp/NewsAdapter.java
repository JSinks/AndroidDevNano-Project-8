package cool.superfcking.apps.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jsinclair on 5/12/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> articles){
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final News news = getItem(position);

        if (convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.newsTitle);
        TextView sectionText = (TextView) convertView.findViewById(R.id.newsSection);

        titleText.setText(news.getTitle());
        sectionText.setText(news.getSection());

        return convertView;

    }
}
