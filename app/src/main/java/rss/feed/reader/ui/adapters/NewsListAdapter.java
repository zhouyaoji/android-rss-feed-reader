package rss.feed.reader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rss.feed.reader.R;
import rss.feed.reader.api.model.News;
import rss.feed.reader.utils.ImageUtils;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    public interface NewsListAdapterInterface {
        void onNewsClick(String newsUrl);
    }

    private NewsListAdapterInterface mListener;
    private List<News> mList;

    NewsListAdapter(){}

    public NewsListAdapter(NewsListAdapterInterface listener){
        mListener = listener;
    }

    public void showNews(List<News> list){
        mList = list;
        notifyDataSetChanged();
    }

    public void addNews(List<News> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_list, parent, false);
        return new NewsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        News news = mList.get(position);
        holder.category.setText(news.getCategory());
        holder.description.setText(news.getDescription());
        holder.time.setText(news.getDate());
        holder.title.setText(news.getTitle());

//        holder.url.setEllipsize(TextUtils.TruncateAt.END);
        holder.url.setText(news.getNewsUrl());

        if (!TextUtils.isEmpty(news.getLogoUrl())) {
            ImageUtils.load(news.getLogoUrl(), holder.logo);
        } else {
            ImageUtils.load(R.drawable.rss_logo, holder.logo);
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_news_list_category)
        TextView category;
        @BindView(R.id.item_news_list_description)
        TextView description;
        @BindView(R.id.item_news_list_logo)
        ImageView logo;
        @BindView(R.id.item_news_list_time)
        TextView time;
        @BindView(R.id.item_news_list_title)
        TextView title;
        @BindView(R.id.item_news_list_url)
        TextView url;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_news_list_parent)
        void onItemClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                mListener.onNewsClick(mList.get(position).getNewsUrl());
            }
        }
    }
}
