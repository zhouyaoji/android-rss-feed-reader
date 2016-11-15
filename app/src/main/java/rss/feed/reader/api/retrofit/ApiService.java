package rss.feed.reader.api.retrofit;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rss.feed.reader.api.model.RssFeed;

/**
 * Created by Andriy Ksenych on 25.10.2016.
 */

public interface ApiService {
@GET
Call<RssFeed> getNewsFeed(@Url String url);
}
