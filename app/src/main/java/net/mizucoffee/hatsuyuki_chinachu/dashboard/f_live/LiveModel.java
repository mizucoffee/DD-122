package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.ChinachuApi;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.broadcasting.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiveModel {

    private ChinachuApi api;
    private DataManager mDataManager;

    private final PublishSubject<List<Program>> liveItemsSubject = PublishSubject.create();
    final Observable<List<Program>> liveItems = (Observable<List<Program>>) liveItemsSubject;

    LiveModel(SharedPreferences sp){
        mDataManager = new DataManager(sp);
        refreshServerConnection();
    }

    public void getServerConnection(DashboardInteractor.OnLoadFinishedListener listener){
        ServerConnection s = mDataManager.getServerConnection();
        if(s == null){
            listener.onNotFound();
            return;
        }
        listener.onSuccess(s);
    }

    private void refreshServerConnection(){
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

    public void getLiveList(){
        refreshServerConnection();
        if(api != null) api.getBroadcasting().enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                liveItemsSubject.onNext(response.body());
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                liveItemsSubject.onNext(null);
            }
        });
    }
}
