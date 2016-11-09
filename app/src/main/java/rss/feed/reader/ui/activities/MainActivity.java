package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rss.feed.reader.R;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.managers.ChannelManager;
import rss.feed.reader.ui.base.BaseMenuActivity;

public class MainActivity extends BaseMenuActivity {

    @Inject
    ChannelManager mChannelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);




        if (mChannelManager.isUserAuthorized()) {
            showProgress(getString(R.string.progress_message_loading));
            mChannelManager.syncChannels();
            mChannelManager.setCallback(new ChannelManager.ChannelsCallback() {
                @Override
                public void callbackSuccess() {
                    hideProgress();
                }

                @Override
                public void callbackFailure() {
                    hideProgress();
                }
            });
        }
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
