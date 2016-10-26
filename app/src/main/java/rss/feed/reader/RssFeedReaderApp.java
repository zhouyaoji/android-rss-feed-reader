package rss.feed.reader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import rss.feed.reader.dagger.components.AppComponent;
import rss.feed.reader.dagger.components.DaggerAppComponent;
import rss.feed.reader.dagger.modules.AppModule;

/**
 * App class.
 * Created by vkravets on 8/31/2016.
 */
public class RssFeedReaderApp extends Application {

    /**
     * Application instance.
     */
    private static RssFeedReaderApp sInstance;
    private static AppComponent mAppComponent;

    private Tracker mTracker;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(getApplicationContext());
        mTracker = analytics.newTracker(R.xml.global_tracker);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
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

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
