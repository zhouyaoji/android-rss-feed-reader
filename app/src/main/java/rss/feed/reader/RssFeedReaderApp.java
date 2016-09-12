package rss.feed.reader;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * App class.
 * Created by vkravets on 8/31/2016.
 */
public class RssFeedReaderApp extends Application {

    /**
     * Application instance.
     */
    private static RssFeedReaderApp sInstance;

    private Tracker mTracker;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(getApplicationContext());
        mTracker = analytics.newTracker(R.xml.global_tracker);
    }

    /**
     * @return Application instance.
     */
    public static RssFeedReaderApp getInstance() {
        return sInstance;
    }

    public Tracker getTracker() {
        return mTracker;
    }
}
