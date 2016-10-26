package rss.feed.reader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.R;
import rss.feed.reader.api.model.Channel;

/**
 * Created by Andriy Ksenych on 03.10.2016.
 */

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder> {

    public interface ChannelsListAdapterInterface{
        void onChannelClick(int position, Channel channel);
    }

    private List<Channel> mList;
    private ChannelsListAdapterInterface mListener;

    public ChannelListAdapter(ChannelsListAdapterInterface listener) {
        mListener = listener;
    }

    public ChannelListAdapter(List<Channel> data, ChannelsListAdapterInterface listener) {
        mList = data;
        mListener = listener;
    }

    public void setData(List<Channel> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Channel channel = mList.get(position);
        holder.title.setText(channel.getTitle());
        holder.url.setText(channel.getUrl());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_channel_list_title)
        TextView title;
        @BindView(R.id.item_channel_list_url)
        TextView url;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_channel_list_parent_view)
        void onItemClick() {
            if (mListener != null) {
                int position = getAdapterPosition();
                mListener.onChannelClick(position, mList.get(position));
            }
        }
    }

}
