package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public interface AddServerInteractor {
    interface OnLoadFinishedListener {
        void onSuccess(ArrayList<ServerConnection> sc);
        void onNotFound();
    }
    void load(SharedPreferences data, OnLoadFinishedListener listener);
    void save(ArrayList<ServerConnection> sc, SharedPreferences data);
}
