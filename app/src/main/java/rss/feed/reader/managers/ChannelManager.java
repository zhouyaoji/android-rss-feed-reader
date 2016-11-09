package rss.feed.reader.managers;

import android.content.SharedPreferences;
import android.support.annotation.MainThread;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import rss.feed.reader.Constants;
import rss.feed.reader.api.model.Channel;


/**
 * Created by Andriy Ksenych on 17.10.2016.
 * Manager for saving/managing user input channels list
 * It isn't multithreading save
 */

public class ChannelManager implements Constants {

    private Gson mGson;
    private SharedPreferences mPreferences;
    private DatabaseReference mReference;
    private ChannelsCallback mCallback;
    private FirebaseAuth mAuth;

    private List<Channel> mChannelsList;

    @MainThread
    public ChannelManager(Gson gson, SharedPreferences preferences, FirebaseDatabase firebaseDatabase) {
        mGson = gson;
        mPreferences = preferences;
        mReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        this.mCallback = null;
        if (mChannelsList == null) {
            mChannelsList = readChannelsList();
        }
    }

    @MainThread
    public List<Channel> getChannelsList() {
        return readChannelsList();
    }

    @MainThread
    public void addChannel(Channel channel) {
        mChannelsList.add(channel);
        addChannelToDB(channel);
        saveChannelsList(mChannelsList);
    }

    @MainThread
    private void addChannels(List<Channel> channels) {
        mChannelsList = readChannelsList();
        mChannelsList.addAll(channels);
        saveChannelsList(mChannelsList);
    }

    @MainThread
    public void removeChannel(int position) {
        removeChannelFromDB(mChannelsList.get(position));
        mChannelsList.remove(position);
        saveChannelsList(mChannelsList);
    }

    @MainThread
    public void editChannel(int position, Channel channel) {
        removeChannelFromDB(mChannelsList.get(position));
        mChannelsList.remove(position);
        mChannelsList.add(position, channel);
        addChannelToDB(channel);
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
        mPreferences.edit().putString(Constants.PREF_CHANNELS_LIST, getJsonStringFromList(list)).apply();
    }

    private List<Channel> readChannelsList() {
        return getChannelsListFromString(mPreferences.getString(Constants.PREF_CHANNELS_LIST, "[]"));
    }

    public void clearAllChannels() {
        mPreferences.edit().putString(Constants.PREF_CHANNELS_LIST, "[]").apply();
        if (mChannelsList != null) {
            mChannelsList.clear();
        }
    }

    /**
     * Firebase section
     */
    public void syncChannels() {
        for (Channel channel : getChannelsList()) {
            addChannelToDB(channel);
        }
        clearAllChannels();
        getFirebaseChannelsList();
    }

    public void setCallback(ChannelsCallback callback) {
        this.mCallback = callback;
    }

    private List<Channel> getFirebaseChannelsList() {
        String uid = getUid();
        final List<Channel> channelList = new ArrayList<>();
        mReference.child(FIREBASE_DB_USERS_KEY).child(uid).child(FIREBASE_DB_CHANNELS_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    channelList.add(postSnapshot.getValue(Channel.class));
                }
                if (mCallback != null) {
                    addChannels(channelList);
                    mCallback.callbackSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mCallback.callbackFailure();
            }
        });
        return channelList;
    }

    private void addChannelToDB(Channel channel) {
        mReference.child(FIREBASE_DB_USERS_KEY).child(getUid()).child(FIREBASE_DB_CHANNELS_KEY).child(channel.getTitle()).setValue(channel);
    }

    private void removeChannelFromDB(Channel channel) {
        mReference.child(FIREBASE_DB_USERS_KEY).child(getUid()).child(FIREBASE_DB_CHANNELS_KEY).child(channel.getTitle()).removeValue();
    }

    private String getUid() {
        return mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "";
    }

    public boolean isUserAuthorized() {
        return !TextUtils.isEmpty(getUid());
    }


    public interface ChannelsCallback {
        void callbackSuccess();

        void callbackFailure();
    }
}
