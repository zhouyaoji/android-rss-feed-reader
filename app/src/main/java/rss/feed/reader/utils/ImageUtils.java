package rss.feed.reader.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import rss.feed.reader.RssFeedReaderApp;

/**
 * Created by Andriy Ksenych on 01.09.2016.
 */
public class ImageUtils {

    public interface ImageCallback {
        void onSuccess(ImageView imageView);

        void onError(String path, ImageView imageView);
    }

    private static final Picasso PICASSO = Picasso.with(RssFeedReaderApp.getInstance());

    /**
     * Load image from internet to imageView without loading callbackSuccess
     *
     * @param path      - image url
     * @param imageView - view of image
     */
    public static void load(@NonNull String path, @NonNull ImageView imageView) {
        PICASSO.load(path).into(imageView);
    }

    /**
     * Load image from internet to imageView with loading callbackSuccess
     *
     * @param path      - image url
     * @param imageView - view of image
     * @param callback  - {@link ImageCallback} callbackSuccess with onSuccess and onError methods loading image url
     */
    public static void load(@NonNull final String path, @NonNull final ImageView imageView, @NonNull final ImageCallback callback) {
        PICASSO.load(path).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess(imageView);
            }

            @Override
            public void onError() {
                callback.onError(path, imageView);
            }
        });
    }

    /**
     * Load image from app resources to imageView
     *
     * @param resId     - id of image resource
     * @param imageView - view of image
     */
    public static void load(int resId, @NonNull ImageView imageView) {
        PICASSO.load(resId).into(imageView);
    }

}
