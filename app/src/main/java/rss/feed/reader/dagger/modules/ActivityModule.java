package rss.feed.reader.dagger.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import rss.feed.reader.dagger.scopes.ActivityScope;

/**
 * Created by Orest Guziy on 16.09.16.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity providesActivity() {
        return mActivity;
    }

}
