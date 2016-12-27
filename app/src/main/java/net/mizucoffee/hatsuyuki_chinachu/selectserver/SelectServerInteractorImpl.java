package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

class SelectServerInteractorImpl implements SelectServerInteractor {

    private DataManager mDataManager;

    SelectServerInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    @Override
    public void load(OnLoadFinishedListener listener){
        String source = mDataManager.loadServerConnections();
        if(source.equals("")){
            listener.onNotFound();
            return;
        }
        listener.onSuccess(DataManager.string2Array(source));
    }

    @Override
    public void delete(int position){
        mDataManager.deleteServerConnection(position);
    }

    @Override
    public ServerConnection edit(int position){
        return mDataManager.getServerConnections(position);
    }
}
