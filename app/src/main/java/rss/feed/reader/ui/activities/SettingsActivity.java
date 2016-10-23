package rss.feed.reader.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;

import rss.feed.reader.R;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.ui.base.BaseActivity;
import rss.feed.reader.ui.fragments.SettingsFragment;

/**
 * Created by omartynets on 29.09.2016.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        getFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}