package rss.feed.reader.analytics.events;

import java.util.Map;

/**
 * Parent class for any Google Analytics events
 * <p/>
 * Created by Orest Guziy on 12.09.16.
 */
public abstract class BaseEvent {

    /**
     * Should provide Google Analytics event
     *
     * @return map that represents Google Analytics Event
     */
    public abstract Map<String, String> getEventData();

}
