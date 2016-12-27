package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

/**
 * Created by mizucoffee on 12/20/16.
 */

public class SelectServerInteractorImpl implements SelectServerInteractor {

    DataManager mDataManager;

    public SelectServerInteractorImpl(SharedPreferences sharedPreferences){
        mDataManager = new DataManager(sharedPreferences);
    }

    @Override
    public void load(OnLoadFinishedListener listener){
        String source = mDataManager.loadServerConnections();
        if(source.equals("")){
            listener.onNotFound();
            return;
        }
        listener.onSuccess(mDataManager.string2Array(source));
    }

    @Override
    public void delete(int position){
        mDataManager.deleteServerConnection(position);
    }
}
