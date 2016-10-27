package rss.feed.reader.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import rss.feed.reader.R;

/**
 * Created by omartynets on 27.10.2016.
 */
public class MessageUtils {

    public static void showSnackBarWithCallback(View view, String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snackbar_control_OK, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        View snackBarView = snackbar.getView();
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(3);
        snackbar.show();
    }
}
