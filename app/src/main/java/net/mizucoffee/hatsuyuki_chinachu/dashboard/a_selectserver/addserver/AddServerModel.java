package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

class AddServerModel {

    private DataManager mDataManager;

    AddServerModel(SharedPreferences sharedPreferences){
        mDataManager = new DataManager(sharedPreferences);
    }

    void addServerConnection(ServerConnection sc){
        sc.setId(System.currentTimeMillis());
        mDataManager.addServerConnection(sc);
    }

    void saveServerConnection(ServerConnection sc, int position){
        mDataManager.editServerConnection(sc,position);
    }
}
