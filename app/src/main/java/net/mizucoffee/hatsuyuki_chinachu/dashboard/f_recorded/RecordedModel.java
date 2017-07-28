package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.ChinachuApi;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataManager;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RecordedModel {

    private final PublishSubject<ArrayList<ProgramItem>> programItemsSubject = PublishSubject.create();
    final Observable<ArrayList<ProgramItem>> programItems = (Observable<ArrayList<ProgramItem>>) programItemsSubject;

    private final PublishSubject<Integer> errorSubject = PublishSubject.create();
    final Observable<Integer> error = (Observable<Integer>) errorSubject;

    static final int NOT_FOUND = 0;
    static final int FAILURE = 1;

    private DataManager mDataManager;

    RecordedModel(SharedPreferences sp){
        mDataManager = new DataManager(sp);
    }

    void getRecordedList(){
        ServerConnection s = mDataManager.getServerConnection();
        if(s == null){
            errorSubject.onNext(NOT_FOUND);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + s.getAddress() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChinachuApi api = retrofit.create(ChinachuApi.class);

        if(api != null) api.getRecorded().enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                ArrayList<ProgramItem> items = new ArrayList<ProgramItem>();
                for(Program p:response.body())
                    items.add(p.getProgramItem(s.getAddress()));
                programItemsSubject.onNext(items);
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                Shirayuki.log("error");
                errorSubject.onNext(FAILURE);
                //TODO: エラー処理
            }
        });
    }
}
