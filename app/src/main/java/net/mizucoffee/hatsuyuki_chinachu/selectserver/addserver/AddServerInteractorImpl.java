package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.ArrayList;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class AddServerInteractorImpl implements AddServerInteractor {

    DataManager mDataManager;

    public AddServerInteractorImpl(SharedPreferences sharedPreferences){
        mDataManager = new DataManager(sharedPreferences);
    }

    @Override
    public void save(ServerConnection sc){
        mDataManager.addServerConnection(sc);
    }

    @Override
    public void edited(ServerConnection sc,int position){
        mDataManager.editServerConnection(sc,position);
    }

    @Override
    public ArrayList<ServerConnection> load(){
        if(mDataManager.loadServerConnections().equals("")) return null;
        return DataManager.string2Array(mDataManager.loadServerConnections());
    }
}
