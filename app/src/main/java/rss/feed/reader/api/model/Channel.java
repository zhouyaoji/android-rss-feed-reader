package rss.feed.reader.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andriy Ksenych on 03.10.2016.
 */

public class Channel implements Parcelable {
    private String title;
    private String url;

    public Channel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    protected Channel(Parcel in) {
        title = in.readString();
        url = in.readString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }
}
