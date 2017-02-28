package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.ChinachuGammaApi;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mizucoffee on 2/27/17.
 */

public class RecordedInteractorImpl implements RecordedInteractor {

    private ChinachuGammaApi api;

    RecordedInteractorImpl(DashboardActivity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + activity.getServerConnection().getAddress() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ChinachuGammaApi.class);
    }

    @Override
    public void getRecordedList(Callback callback){
        api.getRecorded().enqueue(callback);
    }
}
