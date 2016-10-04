package rss.feed.reader.managers;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rss.feed.reader.RssFeedReaderApp;
import rss.feed.reader.api.model.Channel;


/**
 * Created by Andriy Ksenych on 17.10.2016.
 * Manager for saving/managing user input channels list
 */

public class ChannelManager {

    private static final String PREF_CHANNELS_LIST = "channels list";

    public Gson mGson;
    public SharedPreferences mPreferences;

    private List<Channel> mChannelsList;

    public ChannelManager(Gson gson, SharedPreferences preferences) {
        mGson = gson;
        mPreferences = preferences;
    }

    private String getJsonStringFromList(List<Channel> list) {
        return mGson.toJson(list);
    }

    private List<Channel> getChannelsListFromString(String jsonString) {
        return mGson.fromJson(jsonString, new TypeToken<List<Channel>>() {
        }.getType());
    }

    public void saveChannelsList(List<Channel> list) {
        mPreferences.edit().putString(PREF_CHANNELS_LIST, getJsonStringFromList(list)).commit();
    }

    private List<Channel> readChannelsList() {
        return getChannelsListFromString(mPreferences.getString(PREF_CHANNELS_LIST, "[]"));
    }

    public List<Channel> getChannelsList() {
        if (mChannelsList == null) {
            mChannelsList = readChannelsList();
        }
        return mChannelsList;
    }

    public void addChannel(Channel channel) {
        mChannelsList.add(channel);
        saveChannelsList(mChannelsList);
    }

    public void removeChannel(int position) {
        mChannelsList.remove(position);
        saveChannelsList(mChannelsList);
    }

    public void editChannel(int position, Channel channel) {
        mChannelsList.remove(position);
        mChannelsList.add(position, channel);
        saveChannelsList(mChannelsList);
    }
}
