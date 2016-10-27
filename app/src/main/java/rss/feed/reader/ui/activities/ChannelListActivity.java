package rss.feed.reader.ui.activities;

import android.os.Bundle;

import rss.feed.reader.R;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.ui.base.BaseActivity;
import rss.feed.reader.ui.fragments.ChannelListFragment;

/**
 * Created by Andriy Ksenych on 17.10.2016.
 * Activity for showing user's channels list.
 */

public class ChannelListActivity extends BaseActivity {

    private static final String TAG_CHANNEL_LIST_FRAGMENT = "channel list fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_channel_list_container, ChannelListFragment.getInstance(), TAG_CHANNEL_LIST_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
