package rss.feed.reader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rss.feed.reader.api.model.Channel;
import rss.feed.reader.ui.activities.ChannelDetailsActivity;
import rss.feed.reader.ui.activities.ChannelListActivity;

import rss.feed.reader.ui.activities.SettingsActivity;

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
     * Interface that defines a protocol that allows to navigate to some other activity.
     */
    public static void toSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
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
