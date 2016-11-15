package rss.feed.reader.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rss.feed.reader.Navigation;
import rss.feed.reader.R;
import rss.feed.reader.api.model.News;
import rss.feed.reader.dagger.components.ActivityComponent;
import rss.feed.reader.managers.NewsManager;
import rss.feed.reader.ui.adapters.NewsListAdapter;
import rss.feed.reader.ui.base.BaseFragment;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

public class NewsListFragment extends BaseFragment implements NewsListAdapter.NewsListAdapterInterface,
        NewsListInterface {

    @BindView(R.id.fragment_news_list_recyclerView)
    public RecyclerView mListView;
    @BindView(R.id.fragment_news_list_progress)
    public View mProgress;

    @Inject
    public NewsManager mNewsManager;

    private NewsListAdapter mAdapter;
    private List<News> mNewsList = new ArrayList<>();

    private static final String STATE_NEWS_LIST = "NEWS_LIST";

    public static NewsListFragment getInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mListView.setLayoutManager(layoutManager);
        mAdapter = new NewsListAdapter(this);
        mListView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            mNewsManager.getStartUpNewsList(this);
        } else {
            mNewsList = savedInstanceState.getParcelableArrayList(STATE_NEWS_LIST);
            showNews(mNewsList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_NEWS_LIST, new ArrayList<Parcelable>(mNewsList));
    }

    /**
     * Show news list on ListView
     * @param list
     */
    private void showNews(List<News> list) {
        mNewsList = list;
        mAdapter.showNews(list);
    }

    /**
     * Start load RSS feed news
     * @param rssUrl - url of RSS feed
     */
    public void loadNews(String rssUrl) {
        mNewsManager.getNewsList(rssUrl, this);
    }

    /**
     * Load all news from all saved RSS feed
     */
    public void loadAllNews() {
        mNewsManager.getAllNews(this);
    }

    /**
     * Goto webActivity for showing news
     * @param newsUrl - url of news
     */
    @Override
    public void onNewsClick(String newsUrl) {
        if (!TextUtils.isEmpty(newsUrl)) {
            Navigation.toWebActivity(getActivity(), newsUrl);
        } else {
            Toast.makeText(getActivity(), R.string.error_news_url_incorrect, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void injectFragment(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onStartLoadingNews() {
        showProgress();
    }

    @Override
    public void onReceivedNews(List<News> list) {
        hideProgress();
        showNews(list);
    }

    private void showProgress() {
        if (mProgress.getVisibility() != View.VISIBLE) {
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress() {
        if (mProgress.getVisibility() == View.VISIBLE) {
            mProgress.setVisibility(View.GONE);
        }
    }
}
