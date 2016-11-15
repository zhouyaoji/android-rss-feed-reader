package rss.feed.reader.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */
@Root(name = "item", strict=false)
public class RssItem {
    @Element(name = "title")
    String title;
    @Element(name = "link")
    String link;
    @Element(name = "description")
    String description;
    @Element(name = "category")
    String category;
    @Element(name = "pubDate")
    String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPubDate() {
        return pubDate;
    }
}
