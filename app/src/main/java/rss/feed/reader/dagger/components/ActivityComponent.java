package rss.feed.reader.dagger.components;

import android.app.Activity;

import dagger.Component;
import rss.feed.reader.dagger.modules.ActivityModule;
import rss.feed.reader.dagger.scopes.ActivityScope;
import rss.feed.reader.ui.activities.ChannelDetailsActivity;
import rss.feed.reader.ui.activities.ChannelListActivity;
import rss.feed.reader.ui.activities.MainActivity;
import rss.feed.reader.ui.activities.ResetPasswordActivity;
import rss.feed.reader.ui.activities.SettingsActivity;
import rss.feed.reader.ui.activities.SignInActivity;
import rss.feed.reader.ui.activities.SignUpActivity;
import rss.feed.reader.ui.base.BaseMenuActivity;
import rss.feed.reader.ui.base.BaseSignActivity;
import rss.feed.reader.ui.fragments.ChannelDetailsFragment;
import rss.feed.reader.ui.fragments.ChannelListFragment;

/**
 * Created by Orest Guziy on 16.09.16.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity activity();

    void inject(ChannelDetailsFragment fragment);

    void inject(ChannelListFragment fragment);

    void inject(SignInActivity activity);

    void inject(SignUpActivity activity);

    void inject(ResetPasswordActivity activity);

    void inject(ChannelListActivity activity);

    void inject(ChannelDetailsActivity activity);

    void inject(MainActivity activity);

    void inject(SettingsActivity activity);

    void inject(BaseSignActivity activity);

    void inject(BaseMenuActivity activity);
}
