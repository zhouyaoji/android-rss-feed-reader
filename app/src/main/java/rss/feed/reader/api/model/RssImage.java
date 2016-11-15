package rss.feed.reader.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Andriy Ksenych on 25.10.2016.
 */
@Root(name = "image", strict=false)
public class RssImage {
    @Element(name = "url")
    public String url;
    @Element(name = "title")
    public String title;
    @Element(name = "link")
    public String link;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
