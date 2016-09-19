package rss.feed.reader.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rss.feed.reader.Navigation;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.dagger.components.AppComponent;
import rss.feed.reader.dagger.components.DaggerActivityComponent;
import rss.feed.reader.dagger.modules.ActivityModule;

/**
 * Base activity class.
 * Created by vkravets on 8/31/2016.
 */
public class BaseActivity extends AppCompatActivity implements Navigation.ActivityStarter {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // extra code here..
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public Intent createIntent(@NonNull final Class<?> cls) {
        return new Intent(this, cls);
    }

    private AppComponent getAppComponent() {
        return RssFeedReaderApp.getInstance().getAppComponent();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
