package rss.feed.reader.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

public class News implements Parcelable{

    @NonNull String newsUrl;
    @NonNull String logoUrl;
    @NonNull String title;
    @NonNull String category;
    @NonNull String date;
    @NonNull String description;

    public News(@NonNull String newsUrl, @NonNull String logoUrl, @NonNull String title,
                @NonNull String category, @NonNull String date, @NonNull String description) {
        this.newsUrl = newsUrl;
        this.logoUrl = logoUrl;
        this.title = title;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    protected News(Parcel in) {
        newsUrl = in.readString();
        logoUrl = in.readString();
        title = in.readString();
        category = in.readString();
        date = in.readString();
        description = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @NonNull
    public String getLogoUrl() {
        return logoUrl;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsUrl);
        dest.writeString(logoUrl);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(date);
        dest.writeString(description);
    }
}
