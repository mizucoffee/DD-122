package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.app.Activity;
import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mizucoffee on 2/27/17.
 */

public class RecordedPresenterImpl implements RecordedPresenter {

    private RecordedView mRecordedView;
    private RecordedInteractor mRecordedInteractor;

    RecordedPresenterImpl(RecordedView recordedView){
        this.mRecordedView = recordedView;
        mRecordedInteractor = new RecordedInteractorImpl(mRecordedView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public void onDestroy() {
        mRecordedView = null;
    }

    @Override
    public void getRecorded(Activity a){
        mRecordedInteractor.refreshServerConnection();
        mRecordedInteractor.getRecordedList(new Callback<List<Recorded>>() {
            @Override
            public void onResponse(Call<List<Recorded>> call, Response<List<Recorded>> response) {
                mRecordedView.setRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<List<Recorded>> call, Throwable t) {
                mRecordedView.removeRecyclerView();
                t.printStackTrace();
                //TODO: エラー処理
            }
        });
    }
}
