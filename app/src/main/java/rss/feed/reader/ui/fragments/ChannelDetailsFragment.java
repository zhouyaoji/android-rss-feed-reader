package rss.feed.reader.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.R;
import rss.feed.reader.api.model.Channel;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.managers.ChannelManager;
import rss.feed.reader.ui.base.BaseFragment;
import rss.feed.reader.ui.dialogs.ConfirmDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Andriy Ksenych on 13.10.2016.
 * Fragment with channel details. Can add/remove/edit channel.
 */

public class ChannelDetailsFragment extends BaseFragment {

    private static final String ARG_CHANNEL = "extras channel";
    private static final String ARG_CHANNEL_POSITION = "channel position";

    private static final String TAG_DIALOG_CONFIRM = "confirm dialog";

    @BindView(R.id.fragment_channel_details_channel_title)
    EditText mChannelTitle;
    @BindView(R.id.fragment_channel_details_channel_url)
    EditText mChannelUrl;
    @BindView(R.id.fragment_channel_details_btn_save)
    Button mBtnSaveChannel;
    @BindView(R.id.fragment_channel_details_btn_delete)
    Button mBtnDeleteChannel;

    @Inject
    public ChannelManager mChannelsManager;

    /**
     * Interface for making callbackSuccess from channel dialog
     */
    public interface ChannelDetailsInterface {
        void setActivityTitle(String title);
    }

    /**
     * Strategy interface for differences behaviors when channel is added or edited
     */
    private interface ChannelDetailsStrategy {

        void onSaveChannelBtnClick();

        void onDeleteChannelBtnClick();

        void onViewCreated();
    }

    private ChannelDetailsInterface mListener;
    private ChannelDetailsStrategy mStrategy;

    public static ChannelDetailsFragment getInstance(int position, @Nullable Channel channel) {
        ChannelDetailsFragment fragment = new ChannelDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CHANNEL, channel);
        bundle.putInt(ARG_CHANNEL_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setListener(@Nullable ChannelDetailsInterface listener) {
        mListener = listener;
    }

    public ChannelDetailsFragment withListener(@Nullable ChannelDetailsInterface listener) {
        setListener(listener);
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_details, container, false);
        ButterKnife.bind(this, view);

        mStrategy = getStrategy();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStrategy.onViewCreated();
    }

    /** Set up strategy for fragment behavior.
     * @return needed strategy implemented {@link ChannelDetailsStrategy}
     */
    private ChannelDetailsStrategy getStrategy() {
        return (getArguments() != null && getArguments().getParcelable(ARG_CHANNEL) == null) ? new AddChannelStrategy() : new EditChannelStrategy();
    }

    @OnClick(R.id.fragment_channel_details_btn_save)
    void onSaveChannelBtnClick() {
        mStrategy.onSaveChannelBtnClick();
    }

    @OnClick(R.id.fragment_channel_details_btn_delete)
    void onDeleteChannelBtnClick() {
        mStrategy.onDeleteChannelBtnClick();
    }

    public void setResultsAndFinish() {

        Intent intent = new Intent();
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    @Override
    protected void injectFragment(ActivityComponent component) {
        component.inject(this);
    }

    /**
     * Strategy for adding channel to list
     */
    private class AddChannelStrategy implements ChannelDetailsStrategy {

        public AddChannelStrategy() {
            mBtnDeleteChannel.setVisibility(View.GONE);
        }

        @Override
        public void onSaveChannelBtnClick() {
            String title = mChannelTitle.getText().toString();
            String url = mChannelUrl.getText().toString();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(url)) {
                mChannelsManager.addChannel(new Channel(title, url));
                setResultsAndFinish();
            }
        }

        @Override
        public void onDeleteChannelBtnClick() {

        }

        @Override
        public void onViewCreated() {
            if (mListener != null) {
                mListener.setActivityTitle(getString(R.string.activity_channel_details_title_add_new_channel));
            }
        }
    }

    /**
     * Strategy for editing existing channel
     */
    private class EditChannelStrategy implements ChannelDetailsStrategy {
        private Channel mEditedChannel;
        private int mChannelPosition;

        @Override
        public void onSaveChannelBtnClick() {
            if (mEditedChannel != null) {
                mEditedChannel.setUrl(mChannelUrl.getText().toString());
                mEditedChannel.setTitle(mChannelTitle.getText().toString());
                mChannelsManager.editChannel(mChannelPosition, mEditedChannel);
                setResultsAndFinish();
            }
        }

        @Override
        public void onDeleteChannelBtnClick() {
            showConfirmRemoveChannelDialog(mChannelPosition, mEditedChannel);
        }

        @Override
        public void onViewCreated() {
            if (getArguments() != null) {
                mEditedChannel = getArguments().getParcelable(ARG_CHANNEL);
                mChannelPosition = getArguments().getInt(ARG_CHANNEL_POSITION);

                if (mEditedChannel != null && mChannelPosition != INVALID_ITEM_POSITION) { // Edit channel
                    mChannelTitle.setText(mEditedChannel.getTitle());
                    mChannelUrl.setText(mEditedChannel.getUrl());
                    if (mListener != null) {
                        mListener.setActivityTitle(mEditedChannel.getTitle());
                    }
                }
            }
        }

        private void showConfirmRemoveChannelDialog(final int position, final Channel channel) {
            String message = String.format(getString(R.string.dialog_confirm_remove_channel_msg), channel.getTitle());
            ConfirmDialog dialog = ConfirmDialog.getInstance(android.R.string.dialog_alert_title, message);
            dialog.setListener(new ConfirmDialog.ConfirmDialogInterface() {
                @Override
                public void onConfirm() {
                    mChannelsManager.removeChannel(position);
                    setResultsAndFinish();
                }

                @Override
                public void onChannel() {
                    // nothing, just close dialog
                }
            });
            dialog.show(getFragmentManager(), TAG_DIALOG_CONFIRM);
        }
    }
}

