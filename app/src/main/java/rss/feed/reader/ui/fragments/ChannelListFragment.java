package rss.feed.reader.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.Navigation;
import rss.feed.reader.R;
import rss.feed.reader.api.model.Channel;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.managers.ChannelManager;
import rss.feed.reader.ui.activities.ChannelDetailsActivity;
import rss.feed.reader.ui.adapters.ChannelListAdapter;
import rss.feed.reader.ui.base.BaseFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Andriy Ksenych on 03.10.2016.
 * Fragment with channels list.
 */

public class ChannelListFragment extends BaseFragment implements ChannelListAdapter.ChannelsListAdapterInterface {

    @BindView(R.id.fragment_channel_list_recyclerView)
    RecyclerView mList;

    private ChannelListAdapter mAdapter;

    @Inject
    public ChannelManager mChannelManager;

    public static ChannelListFragment getInstance() {
        return new ChannelListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectFragment(ActivityComponent component) {
        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(layoutManager);
        mAdapter = new ChannelListAdapter(this);
        mList.setAdapter(mAdapter);

        showChannelsList(mChannelManager.getChannelsList());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onChannelClick(int position, Channel channel) {
        showChannelDetailsActivity(position, channel);
    }

    @OnClick(R.id.fragment_channel_list_fab_add)
    public void onAddChannelBtnClick() {
        showChannelDetailsActivity(INVALID_ITEM_POSITION, null);
    }

    public void showChannelsList(List<Channel> channelsList) {
        mAdapter.setData(channelsList);
    }

    public void showChannelDetailsActivity(int position, @Nullable Channel channel) {
        Navigation.toChannelDetails(this, position, channel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if new channel was added - refresh the list
        if (requestCode == ChannelDetailsActivity.EDIT_CHANNEL_REQUEST) {
            if (resultCode == RESULT_OK) {
                mAdapter.setData(mChannelManager.getChannelsList());
            }
        }
    }
}