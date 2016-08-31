package rss.feed.reader.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rss.feed.reader.Navigation;

/**
 * Base activity class.
 * Created by vkravets on 8/31/2016.
 */
public class BaseActivity extends AppCompatActivity implements Navigation.ActivityStarter {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // extra code here..
    }

    @Override
    public Intent createIntent(@NonNull final Class<?> cls) {
        return new Intent(this, cls);
    }
}
