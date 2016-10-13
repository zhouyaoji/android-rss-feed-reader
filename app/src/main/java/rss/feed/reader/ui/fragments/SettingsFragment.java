package rss.feed.reader.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import rss.feed.reader.R;

/**
 * Created by omartynets on 29.09.2016.
 */
public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
