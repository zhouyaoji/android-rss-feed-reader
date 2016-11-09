package rss.feed.reader.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.managers.ChannelManager;

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

    @Provides
    @Singleton
    public FirebaseAuth providesFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseDatabase providesFirebaseDatabaseInstance(){
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    public ChannelManager providesChannelManager(Gson gson, SharedPreferences preferences, FirebaseDatabase firebaseDatabase){
        return new ChannelManager(gson, preferences, firebaseDatabase);
    }
}
