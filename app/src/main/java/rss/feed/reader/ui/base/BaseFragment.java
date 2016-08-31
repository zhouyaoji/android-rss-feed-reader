package rss.feed.reader.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rss.feed.reader.Navigation;

/**
 * Base fragment class.
 * Created by vkravets on 8/31/2016.
 */
public class BaseFragment extends Fragment implements Navigation.ActivityStarter {

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // extra code here..
    }

    @Override
    public Intent createIntent(@NonNull final Class<?> cls) {
        return new Intent(getContext(), cls);
    }
}
