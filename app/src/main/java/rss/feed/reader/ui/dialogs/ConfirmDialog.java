package rss.feed.reader.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andriy Ksenych on 12.10.2016.
 * Dialog for showing confirmation messages with two buttons - ok and cancel
 */

public class ConfirmDialog extends android.support.v4.app.DialogFragment {

    private static final String ARG_TITLE = "arg dialog title";
    private static final String ARG_MESSAGE = "arg dialog message";

    /**
     * Interface for making callbacks from Dialog
     */
    public interface ConfirmDialogInterface {
        void onConfirm();

        void onChannel();
    }

    private ConfirmDialogInterface mListener;

    /**
     * Create confirmation dialog with title and message
     *
     * @param title   - int resource of title
     * @param message - string confirmation message
     * @return dialog
     */
    public static ConfirmDialog getInstance(int title, @NonNull String message) {
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TITLE, title);
        bundle.putString(ARG_MESSAGE, message);
        dialog.setArguments(bundle);
        return dialog;
    }

    /**
     * Create confirmation dialog without title
     *
     * @param message - confirmation message
     * @return dialog
     */
    public static ConfirmDialog getInstance(@NonNull String message) {
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MESSAGE, message);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setListener(@Nullable ConfirmDialogInterface listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = getArguments().getString(ARG_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onConfirm();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onChannel();
                        }
                    }
                });

        int title = getArguments().getInt(ARG_TITLE);
        if (title != 0) {
            builder.setTitle(title);
        }

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }
}
