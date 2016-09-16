package rss.feed.reader.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import rss.feed.reader.RssFeedReaderApp;

/**
 * Created by Orest Guziy on 16.09.16.
 */
@Module
public class AppModule {

    private final RssFeedReaderApp mApp;

    public AppModule(RssFeedReaderApp app) {
        mApp = app;
    }

    @Provides
    public Context appContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    public RssFeedReaderApp app() {
        return mApp;
    }
}
