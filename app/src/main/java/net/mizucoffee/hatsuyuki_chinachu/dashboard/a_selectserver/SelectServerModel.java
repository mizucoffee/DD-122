package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class SelectServerModel{

    private DataManager mDataManager;

    private final PublishSubject<ArrayList<ServerConnection>> serverConnectionsSubject = PublishSubject.create();
    final Observable<ArrayList<ServerConnection>> serverConnections = (Observable<ArrayList<ServerConnection>>) serverConnectionsSubject;

    SelectServerModel(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    void loadServerConnections(){
        String source = mDataManager.loadServerConnections();
        if(source.equals("")){
            serverConnectionsSubject.onNext(new ArrayList<>());
            return;
        }
        serverConnectionsSubject.onNext(DataManager.string2Array(source));
    }

    void deleteServerConnection(int position){
        mDataManager.deleteServerConnection(position);
    }

    ServerConnection editServerConnection(int position){
        return mDataManager.getServerConnections(position);
    }

    void setServerConnection(ServerConnection sc){
        mDataManager.setServerConnection(sc);
    }

    public ServerConnection getServerConnection(){
        return mDataManager.getServerConnection();
    }
}
