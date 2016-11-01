package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;
import rss.feed.reader.R;
import rss.feed.reader.api.model.Channel;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.ui.base.BaseActivity;
import rss.feed.reader.ui.fragments.ChannelDetailsFragment;

/**
 * Created by Andriy Ksenych on 17.10.2016.
 * Activity for showing/adding/removing details of channel
 */

public class ChannelDetailsActivity extends BaseActivity implements ChannelDetailsFragment.ChannelDetailsInterface {

    public static final String EXTRAS_CHANNEL = "selected channel";
    public static final String EXTRAS_POSITION = "channel position";

    private static final String TAG_CHANNEL_DETAILS_FRAGMENT = "channel details fragment";
    public static final int EDIT_CHANNEL_REQUEST = 1;

    @BindView(R.id.activity_channel_details_collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.activity_channel_details_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_details);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            Channel channel = getIntent().getParcelableExtra(EXTRAS_CHANNEL);
            int position = getIntent().getIntExtra(EXTRAS_POSITION, 0);

            ChannelDetailsFragment fragment = ChannelDetailsFragment.getInstance(position, channel).withListener(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_channel_details_container, fragment, TAG_CHANNEL_DETAILS_FRAGMENT)
                    .commit();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CHANNEL_DETAILS_FRAGMENT);
            if (fragment != null) {
                ((ChannelDetailsFragment) fragment).setListener(this);
            }
        }
    }

    @Override
    public void setActivityTitle(String title) {
        mCollapsingToolBar.setTitle(title);
    }

    /**
     * Remove base menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
