package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.ArrayList;

class AddServerModel {

    private DataManager mDataManager;

    AddServerModel(SharedPreferences sharedPreferences){
        mDataManager = new DataManager(sharedPreferences);
    }

    public void addServerConnection(ServerConnection sc){
        sc.setId(System.currentTimeMillis());
        mDataManager.addServerConnection(sc);
    }

    public void saveServerConnection(ServerConnection sc, int position){
        mDataManager.editServerConnection(sc,position);
    }

    public ArrayList<ServerConnection> loadServerConnections(){
        if(mDataManager.loadServerConnections().equals("")) return null;
        return DataManager.string2Array(mDataManager.loadServerConnections());
    }
}
