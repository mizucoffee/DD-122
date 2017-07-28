package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DashboardModel {

    private final PublishSubject<ServerConnection> serverConnectionSubject = PublishSubject.create();
    final Observable<ServerConnection> serverConnection = (Observable<ServerConnection>) serverConnectionSubject;

    private final PublishSubject<String> errorSubject = PublishSubject.create();
    final Observable<String> error = (Observable<String>) errorSubject;

    private DataManager mDataManager;

    DashboardModel(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    public void getServerConnection(){
        ServerConnection s = mDataManager.getServerConnection();
        if(s == null){
            errorSubject.onNext("");
            return;
        }
        serverConnectionSubject.onNext(s);
    }
}
