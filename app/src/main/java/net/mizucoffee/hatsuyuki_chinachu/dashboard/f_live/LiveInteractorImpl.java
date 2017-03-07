package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.ChinachuApi;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiveInteractorImpl implements LiveInteractor {

    private ChinachuApi api;
    private DataManager mDataManager;

    LiveInteractorImpl(SharedPreferences sp){
        mDataManager = new DataManager(sp);
        refreshServerConnection();
    }

    @Override
    public void getServerConnection(DashboardInteractor.OnLoadFinishedListener listener){
        ServerConnection s = mDataManager.getServerConnection();
        if(s == null){
            listener.onNotFound();
            return;
        }
        listener.onSuccess(s);
    }

    @Override
    public void refreshServerConnection(){
        getServerConnection(new DashboardInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ServerConnection sc) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + sc.getAddress() + "/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                api = retrofit.create(ChinachuApi.class);
            }

            @Override
            public void onNotFound() {

            }
        });
    }

    @Override
    public void getLiveList(Callback callback){
        if(api != null) api.getBroadcasting().enqueue(callback);
    }
}
