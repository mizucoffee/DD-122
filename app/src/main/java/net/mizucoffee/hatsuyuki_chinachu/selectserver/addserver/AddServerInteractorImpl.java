package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.ArrayList;

class AddServerInteractorImpl implements AddServerInteractor {

    DataManager mDataManager;

    AddServerInteractorImpl(SharedPreferences sharedPreferences){
        mDataManager = new DataManager(sharedPreferences);
    }

    @Override
    public void addServerConnection(ServerConnection sc){
        mDataManager.addServerConnection(sc);
    }

    @Override
    public void editedServerConnection(ServerConnection sc, int position){
        mDataManager.editServerConnection(sc,position);
    }

    @Override
    public ArrayList<ServerConnection> loadServerConnections(){
        if(mDataManager.loadServerConnections().equals("")) return null;
        return DataManager.string2Array(mDataManager.loadServerConnections());
    }
}
