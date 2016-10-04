package rss.feed.reader.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.dagger.modules.AppModule;
import rss.feed.reader.managers.ChannelManager;

/**
 * Singleton-based app component
 * <p/>
 * Created by Orest Guziy on 16.09.16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    RssFeedReaderApp app();

    ChannelManager getChannelManager();
}
