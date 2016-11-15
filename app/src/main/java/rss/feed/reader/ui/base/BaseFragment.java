package rss.feed.reader.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import rss.feed.reader.Constants;
import rss.feed.reader.Navigation;
import rss.feed.reader.dagger.components.ActivityComponent;

/**
 * Base fragment class.
 * Created by vkravets on 8/31/2016.
 */
public abstract class BaseFragment extends Fragment implements Navigation.ActivityStarter, Constants {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // extra code here..
        BaseActivity activity = (BaseActivity) getActivity();
        injectFragment(activity.getActivityComponent());
    }

    @Override
    public Intent createIntent(@NonNull final Class<?> cls) {
        return new Intent(getContext(), cls);
    }

    protected abstract void injectFragment(ActivityComponent component);
}
