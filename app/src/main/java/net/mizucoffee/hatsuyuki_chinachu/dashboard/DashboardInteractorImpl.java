package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

public class DashboardInteractorImpl implements DashboardInteractor {

    private DataManager mDataManager;

    DashboardInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    @Override
    public void getServerConnection(OnLoadFinishedListener listener){
        ServerConnection s = mDataManager.getServerConnection();
        if(s == null){
            listener.onNotFound();
            return;
        }
        listener.onSuccess(s);
    }
}
