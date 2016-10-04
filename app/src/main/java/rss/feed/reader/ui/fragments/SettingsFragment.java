package rss.feed.reader.ui.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import rss.feed.reader.Navigation;
import rss.feed.reader.R;
import rss.feed.reader.ui.activities.SettingsActivity;

/**
 * Created by omartynets on 29.09.2016.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String KEY_PREF_CHANNELS_LIST = "channels_list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference(KEY_PREF_CHANNELS_LIST).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case KEY_PREF_CHANNELS_LIST:
                Navigation.toChannelsList((SettingsActivity) getActivity());
                break;
        }
        return false;
    }
}
