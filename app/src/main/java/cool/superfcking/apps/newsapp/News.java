package cool.superfcking.apps.newsapp;

/**
 * Created by jsinclair on 5/12/16.
 */
public class News {

    private String mTitle;
    private String mSection;
    private String mUrl;
    private String mAuthor;
    private String mPublicationDate;

    public News(String title, String section, String url){
        mTitle = title;
        mSection = section;
        mUrl = url;
        mAuthor = "";
        mPublicationDate = "";
    }

    public String getTitle(){
        return mTitle;
    }

    public String getSection(){
        return mSection;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getPublicationDate(){
        return mPublicationDate;
    }
}
