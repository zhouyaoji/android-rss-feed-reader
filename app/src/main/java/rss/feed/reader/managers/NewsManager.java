package rss.feed.reader.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rss.feed.reader.R;
import rss.feed.reader.api.model.News;
import rss.feed.reader.api.model.RssChannel;
import rss.feed.reader.api.model.RssFeed;
import rss.feed.reader.api.model.RssImage;
import rss.feed.reader.api.model.RssItem;
import rss.feed.reader.api.retrofit.ApiProvider;
import rss.feed.reader.api.retrofit.Callback;
import rss.feed.reader.api.retrofit.Error;
import rss.feed.reader.ui.fragments.NewsListInterface;
import rss.feed.reader.utils.TimeUtils;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 * Manager for managing News, making network requests for news
 */

public class NewsManager {
    private static final String PREF_KEY_STARTUP_NEWS = "startup_news_selector";
    private static final String PREF_ALL_NEWS = "All news";
    private static final String PREF_FIRST_RSS = "First rss";

    private static final String EMPTY_VALUE = "";

    private ApiProvider mApiProvider;
    private ChannelManager mChannelManager;
    private Context mContext;

    public NewsManager(ApiProvider apiProvider, ChannelManager channelManager, Context context) {
        mApiProvider = apiProvider;
        mChannelManager = channelManager;
        mContext  = context;
    }

    /**
     * Get list of {@link News} from {@link RssFeed}
     * @param rssFeed - model of RSS feed
     * @return list of news
     */
    @MainThread
    private static List<News> getNewsListFromRssFeed(@Nullable RssFeed rssFeed) {

        List<News> newsList = new ArrayList<>();

        if (rssFeed == null || rssFeed.getChannel() == null || rssFeed.getChannel().getItemsList() == null) {
            return newsList;
        }

        RssChannel rssChannel = rssFeed.getChannel();
        RssImage rssImage = rssChannel.getImage();

        String siteTitle = rssImage.getTitle();
        String siteLogo = rssImage.getUrl();

        for (RssItem item : rssChannel.getItemsList()) {
            News news = new News(
                    item.getLink() != null ? item.getLink() : EMPTY_VALUE,
                    siteLogo != null ? siteLogo : EMPTY_VALUE,
                    item.getTitle() != null ? item.getTitle() : EMPTY_VALUE,
                    item.getCategory() != null ? item.getCategory() : EMPTY_VALUE,
                    item.getPubDate() != null ? TimeUtils.getPrettyTime(item.getPubDate()) : EMPTY_VALUE,
                    item.getDescription() != null ? item.getDescription() : EMPTY_VALUE
            );
            newsList.add(news);
        }
        return newsList;
    }

    /**
     * Get list of news which should be shown when app is started at the first time.
     * Depends on what behavior is set on Settings
     * @param listener
     */
    public void getStartUpNewsList(NewsListInterface listener){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String startNews = preferences.getString(PREF_KEY_STARTUP_NEWS, PREF_FIRST_RSS);
        StartUpLoadVariant startVariant;
        switch(startNews){
            case PREF_ALL_NEWS:
                startVariant = StartUpLoadVariant.ALL_NEWS;
                break;
            case PREF_FIRST_RSS:
                startVariant = StartUpLoadVariant.FIRST_RSS_NEWS;
                break;
            default:
                startVariant = StartUpLoadVariant.FIRST_RSS_NEWS;
        }
        startVariant.getNews(mChannelManager, this, listener);
    }

    /**
     * get list of all {@link News} from all saved {@link rss.feed.reader.api.model.Channel}
     * @param listener
     */
    public void getAllNews(NewsListInterface listener){
        StartUpLoadVariant.ALL_NEWS.getNews(mChannelManager, this, listener);
    }

    /**
     * get list of {@link News} and load it to callback from RSS url
     * @param url - url of RSS feed
     * @param callback - callback for receiving list of {@link News}
     */
    public void getNewsList(String url, final NewsListInterface callback) {

        if (callback != null) {
            callback.onStartLoadingNews();
        }

        mApiProvider.getRssFeed(url, new Callback<RssFeed>() {
            @Override
            public void onSuccess(RssFeed response) {
                if (callback != null) {
                    callback.onReceivedNews(getNewsListFromRssFeed(response));
                }
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(mContext, R.string.error_general_error,Toast.LENGTH_SHORT).show();
            }
        });
    }

}

