package rss.feed.reader.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

@Root(name = "channel", strict=false)
public class RssChannel {
    @Element(name = "image")
    public RssImage image;
    @ElementList(inline = true)
    List<RssItem> itemsList;

    public RssImage getImage() {
        return image;
    }

    public List<RssItem> getItemsList() {
        return itemsList;
    }
}
