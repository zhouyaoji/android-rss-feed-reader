package rss.feed.reader;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * App navigation.
 * Created by vkravets on 8/31/2016.
 */
public final class Navigation {

    /** Navigates to Help page. */
    public static void toHelp(@NonNull ActivityStarter activityStarter) {
        // TODO:
    }

    /** Navigates to web page that specified in {@code url} param. It will be opened
     * in a default system browser. */
    public static void toWebBrowser(@NonNull ActivityStarter starter, @NonNull String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        starter.startActivity(intent);
    }

    /** Interface that defines a protocol that allows to navigate to some other activity. */
    public interface ActivityStarter {

        void startActivity(Intent intent);

        void startActivityForResult(Intent intent, int requestCode);

        Intent createIntent(@NonNull Class<?> cls);

    }
}
