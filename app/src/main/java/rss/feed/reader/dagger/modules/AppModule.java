package rss.feed.reader.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.api.retrofit.ApiProvider;
import rss.feed.reader.api.retrofit.RetrofitApiProvider;
import rss.feed.reader.managers.ChannelManager;
import rss.feed.reader.managers.NewsManager;

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
    public Context providesAppContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    public RssFeedReaderApp providesApp() {
        return mApp;
    }

    @Provides
    public SharedPreferences providesPreferences(){
        return mApp.getSharedPreferences("RssFeedReaderApp", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    public ChannelManager providesChannelManager(Gson gson, SharedPreferences preferences){
        return new ChannelManager(gson, preferences);
    }

    @Singleton
    @Provides
    public ApiProvider providesApiProvider(){
        return new RetrofitApiProvider();
    }

    @Provides
    public NewsManager providesNewsManager(ApiProvider apiProvider, ChannelManager channelManager, Context context){
        return new NewsManager(apiProvider, channelManager, context);
    }
}
