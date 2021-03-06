package rss.feed.reader.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import rss.feed.reader.Navigation;
import rss.feed.reader.R;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.dagger.components.AppComponent;
import rss.feed.reader.dagger.components.DaggerActivityComponent;
import rss.feed.reader.dagger.modules.ActivityModule;

/**
 * Base activity class.
 * Created by vkravets on 8/31/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements Navigation.ActivityStarter {

    private ActivityComponent mActivityComponent;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // extra code here..
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();

        injectActivity(mActivityComponent);

        mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
    }

    @Override
    public Intent createIntent(@NonNull final Class<?> cls) {
        return new Intent(this, cls);
    }

    public AppComponent getAppComponent() {
        return RssFeedReaderApp.getInstance().getAppComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    protected void hideProgress() {
        mProgressDialog.dismiss();
    }

    protected void showProgress(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract void injectActivity(ActivityComponent activityComponent);
}
