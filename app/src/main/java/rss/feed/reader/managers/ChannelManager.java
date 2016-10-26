package rss.feed.reader.managers;

import android.content.SharedPreferences;
import android.support.annotation.MainThread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import rss.feed.reader.api.model.Channel;


/**
 * Created by Andriy Ksenych on 17.10.2016.
 * Manager for saving/managing user input channels list
 * It isn't multithreading save
 */

public class ChannelManager {

    private static final String PREF_CHANNELS_LIST = "channels list";

    private Gson mGson;
    private SharedPreferences mPreferences;

    private List<Channel> mChannelsList;

    @MainThread
    public ChannelManager(Gson gson, SharedPreferences preferences) {
        mGson = gson;
        mPreferences = preferences;
    }

    @MainThread
    public List<Channel> getChannelsList() {
        if (mChannelsList == null) {
            mChannelsList = readChannelsList();
        }
        return mChannelsList;
    }

    @MainThread
    public void addChannel(Channel channel) {
        mChannelsList.add(channel);
        saveChannelsList(mChannelsList);
    }

    @MainThread
    public void removeChannel(int position) {
        mChannelsList.remove(position);
        saveChannelsList(mChannelsList);
    }

    @MainThread
    public void editChannel(int position, Channel channel) {
        mChannelsList.remove(position);
        mChannelsList.add(position, channel);
        saveChannelsList(mChannelsList);
    }

    private String getJsonStringFromList(List<Channel> list) {
        return mGson.toJson(list);
    }

    private List<Channel> getChannelsListFromString(String jsonString) {
        return mGson.fromJson(jsonString, new TypeToken<List<Channel>>() {
        }.getType());
    }

    private void saveChannelsList(List<Channel> list) {
        mPreferences.edit().putString(PREF_CHANNELS_LIST, getJsonStringFromList(list)).commit();
    }

    private List<Channel> readChannelsList() {
        return getChannelsListFromString(mPreferences.getString(PREF_CHANNELS_LIST, "[]"));
    }
}
