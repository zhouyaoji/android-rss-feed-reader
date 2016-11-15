package rss.feed.reader.api.retrofit;

import rss.feed.reader.api.model.RssFeed;

/**
 * Created by Andriy Ksenych on 04.11.2016.
 */

public interface ApiProvider {
    void getRssFeed(String url, Callback<RssFeed> callback);
}
