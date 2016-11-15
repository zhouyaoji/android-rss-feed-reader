package rss.feed.reader.api.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rss.feed.reader.api.model.RssFeed;

/**
 * Created by Andriy Ksenych on 25.10.2016.
 */

public class RetrofitApiProvider implements ApiProvider{

    private static final String BASE_URL = "http://www.pravda.com.ua/";

    private ApiService mApiService;

    public RetrofitApiProvider(){
        this.mApiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Override
    public void getRssFeed(String url, Callback<RssFeed> callback) {
        mApiService.getNewsFeed(url).enqueue(callback);
    }
}
