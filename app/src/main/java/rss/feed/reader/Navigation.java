package rss.feed.reader;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rss.feed.reader.api.model.Channel;
import rss.feed.reader.ui.activities.ChannelDetailsActivity;
import rss.feed.reader.ui.activities.ChannelListActivity;
import rss.feed.reader.ui.activities.MainActivity;
import rss.feed.reader.ui.activities.ResetPasswordActivity;
import rss.feed.reader.ui.activities.SettingsActivity;
import rss.feed.reader.ui.activities.SignInActivity;
import rss.feed.reader.ui.activities.SignUpActivity;

/**
 * App navigation.
 * Created by vkravets on 8/31/2016.
 */
public final class Navigation {

    /**
     * Navigates to Help page.
     */
    public static void toHelp(@NonNull ActivityStarter activityStarter) {
        // TODO:
    }

    /**
     * Navigates to web page that specified in {@code url} param. It will be opened
     * in a default system browser.
     */
    public static void toWebBrowser(@NonNull ActivityStarter starter, @NonNull String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        starter.startActivity(intent);
    }

    /**
     * Navigates to channels list activity
     */
    public static void toChannelsList(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(ChannelListActivity.class));
    }

    /**
     * Navigates to channel detail activity
     */
    public static void toChannelDetails(@NonNull ActivityStarter starter, int position, @Nullable Channel channel) {
        Intent intent = starter.createIntent(ChannelDetailsActivity.class);
        intent.putExtra(ChannelDetailsActivity.EXTRAS_CHANNEL, channel);
        intent.putExtra(ChannelDetailsActivity.EXTRAS_POSITION, position);
        starter.startActivityForResult(intent, ChannelDetailsActivity.EDIT_CHANNEL_REQUEST);
    }

    /**
     * Navigates to Settings Activity.
     */
    public static void toSettingsActivity(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(SettingsActivity.class));
    }

    /**
     * Navigates to SignInActivity Activity.
     */
    public static void toSignInActivity(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(SignInActivity.class));
    }

    /**
     * Navigates to SignUpActivity Activity.
     */
    public static void toSignUpActivity(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(SignUpActivity.class));
    }

    /**
     * Navigates to Main Activity
     */
    public static void toMainActivity(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(MainActivity.class));
    }

    public static void toResetPasswordActivity(@NonNull ActivityStarter starter) {
        starter.startActivity(starter.createIntent(ResetPasswordActivity.class));
    }

    /**
     * Interface that defines a protocol that allows to navigate to some other activity.
     */
    public interface ActivityStarter {

        void startActivity(Intent intent);

        void startActivityForResult(Intent intent, int requestCode);

        Intent createIntent(@NonNull Class<?> cls);

    }
}
