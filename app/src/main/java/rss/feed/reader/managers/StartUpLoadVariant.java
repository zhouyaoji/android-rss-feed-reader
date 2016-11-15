package rss.feed.reader.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rss.feed.reader.api.model.Channel;
import rss.feed.reader.api.model.News;
import rss.feed.reader.ui.fragments.NewsListInterface;

/**
 * Created by Andriy Ksenych on 14.11.2016.
 * Strategy for loading news when the app started at the first time
 */

public enum StartUpLoadVariant {

    FIRST_RSS_NEWS {
        @Override
        public void getNews(ChannelManager channelManager, NewsManager newsManager, final NewsListInterface listener) {
            if (listener != null) {
                if (!channelManager.getChannelsList().isEmpty()) {
                    newsManager.getNewsList(channelManager.getChannelsList().get(0).getUrl(), new NewsListInterface() {
                        @Override
                        public void onStartLoadingNews() {
                            listener.onStartLoadingNews();
                        }

                        @Override
                        public void onReceivedNews(List<News> list) {
                            if (listener != null) {
                                listener.onReceivedNews(list);
                            }
                        }
                    });
                } else {
                    listener.onReceivedNews(Collections.EMPTY_LIST);
                }
            }
        }
    },

    ALL_NEWS {

        private int channelsNumber;
        private List<News> mAllNewsList = new ArrayList<>();

        @Override
        public void getNews(final ChannelManager channelManager, NewsManager newsManager, final NewsListInterface listener) {
            channelsNumber = 0;
            if (listener != null) {
                listener.onStartLoadingNews();

                if (!channelManager.getChannelsList().isEmpty()) {

                    for (Channel channel : channelManager.getChannelsList()) {
                        newsManager.getNewsList(channel.getUrl(), new NewsListInterface() {
                            @Override
                            public void onStartLoadingNews() {
                                //just nothing, have relax ...
                            }

                            @Override
                            public synchronized void onReceivedNews(List<News> list) {
                                mAllNewsList.addAll(list);
                                channelsNumber++;
                                if (channelsNumber == channelManager.getChannelsList().size() && listener != null) {
                                    listener.onReceivedNews(mAllNewsList);
                                }
                            }
                        });
                    }
                } else {
                    listener.onReceivedNews(Collections.EMPTY_LIST);
                }
            }
        }
    };

    public abstract void getNews(ChannelManager channelManager, NewsManager newsManager, NewsListInterface listener);
}
