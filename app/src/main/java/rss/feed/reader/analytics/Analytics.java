package rss.feed.reader.analytics;

import com.google.android.gms.analytics.Tracker;

import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.analytics.events.BaseEvent;

/**
 * Base class that should be used for sending analytics events
 * <p/>
 * Created by Orest Guziy on 12.09.16.
 */
public final class Analytics {

    private Analytics() {
    }

    /**
     * Respond for sending Google Analytics events
     *
     * @param event instance
     */
    public static void send(BaseEvent event) {
        Tracker tracker = RssFeedReaderApp.getInstance().getTracker();
        tracker.send(event.getEventData());
    }
}
