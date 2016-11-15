package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rss.feed.reader.R;
import rss.feed.reader.api.model.Channel;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.managers.ChannelManager;
import rss.feed.reader.ui.base.BaseMenuActivity;
import rss.feed.reader.ui.fragments.NewsListFragment;

public class MainActivity extends BaseMenuActivity {

    private static final String TAG_NEWS_LIST_FRAGMENT = "news list fragment";
    @Inject
    public ChannelManager mChannelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            NewsListFragment fragment = NewsListFragment.getInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.news_list_container, fragment, TAG_NEWS_LIST_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /**
     * Show news from selected channel
     * @param item - menu item of channel
     */
    @Override
    public void onNewsItemSelected(@NonNull MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_NEWS_LIST_FRAGMENT);
        if (fragment != null) {
            ((NewsListFragment) fragment).loadNews(mChannelManager.getChannelsList().get(item.getItemId()).getUrl());
            item.setChecked(true);
        }
    }

    /**
     * Show all news from all saved channels
     */
    @Override
    public void loadAllNews() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_NEWS_LIST_FRAGMENT);
        if (fragment != null) {
            ((NewsListFragment) fragment).loadAllNews();
        }
    }

    @Override
    public void setUpNavigationViewContent() {

        List<Channel> channelList = mChannelManager.getChannelsList();

        Menu menu = getMenu();
        menu.removeGroup(R.id.menu_news_list_item);

        int size = channelList.size();
        for (int i = 0; i < size; i++) {
            menu.add(R.id.menu_news_list_item, i, Menu.NONE, channelList.get(i).getTitle());
        }
    }

    /**
     * dismiss actionbar menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
