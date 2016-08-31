package rss.feed.reader;

import android.app.Application;

/**
 * App class.
 * Created by vkravets on 8/31/2016.
 */
public class RssFeedReaderApp extends Application {

    /** Application instance. */
    private static RssFeedReaderApp sInstance;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();

        // extra code here
    }

    /**
     * @return Application instance.
     */
    public static RssFeedReaderApp getInstance() {
        return sInstance;
    }
}
