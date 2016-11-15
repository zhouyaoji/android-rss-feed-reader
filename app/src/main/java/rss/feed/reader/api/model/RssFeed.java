package rss.feed.reader.api.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Andriy Ksenych on 25.10.2016.
 */
@Root(name = "rss", strict=false)
public class RssFeed {
    @Attribute(name = "version")
    String version;
    @Element(name = "channel")
    RssChannel channel;

    public String getVersion() {
        return version;
    }

    public RssChannel getChannel() {
        return channel;
    }
}
