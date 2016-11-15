package rss.feed.reader.ui.fragments;

import java.util.List;

import rss.feed.reader.api.model.News;

/**
 * Created by Andriy Ksenych on 10.11.2016.
 */

public interface NewsListInterface {
    /**
     * Called when {@link rss.feed.reader.managers.NewsManager} start loading RSS feed
     */
    void onStartLoadingNews();

    /**
     * Called when {@link rss.feed.reader.managers.NewsManager} finish loading RSS feed
     */
    void onReceivedNews(List<News> list);
}
