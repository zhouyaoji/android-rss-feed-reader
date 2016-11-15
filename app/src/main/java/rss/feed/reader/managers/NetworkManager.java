package rss.feed.reader.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.api.model.RssFeed;

/**
 * @author Andrii Ksenych
 * Manager for managing network connectivity
 */
public class NetworkManager {
    /**
     * Check is network enabled
     * @return - is network enabled
     */
    public static boolean isNetworkEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RssFeedReaderApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}
