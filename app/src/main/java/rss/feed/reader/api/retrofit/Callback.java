package rss.feed.reader.api.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import rss.feed.reader.R;
import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.managers.NetworkManager;

/**
 * Created by Andriy Ksenych on 25.10.2016.
 */

public abstract class Callback<T> implements retrofit2.Callback<T> {

    public abstract void onSuccess(@Nullable T response);

    public abstract void onError(@NonNull Error error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError(new Error(response.raw().code(), response.raw().message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!NetworkManager.isNetworkEnabled()) {
            onError(new Error(Error.ERROR_NO_INTERNET_CONNECTION, t.getMessage(), t));
            Toast.makeText(RssFeedReaderApp.getInstance(), R.string.error_no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO handle another types of errors
    }

}
