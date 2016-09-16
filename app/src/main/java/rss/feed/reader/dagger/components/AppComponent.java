package rss.feed.reader.dagger.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.dagger.modules.AppModule;

/**
 * Singleton-based app component
 * <p/>
 * Created by Orest Guziy on 16.09.16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context appContext();

    RssFeedReaderApp app();

}
