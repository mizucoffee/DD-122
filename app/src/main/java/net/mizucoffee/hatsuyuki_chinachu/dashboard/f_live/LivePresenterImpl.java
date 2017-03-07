package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.broadcasting.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivePresenterImpl implements LivePresenter {

    private LiveView mLiveView;
    private LiveInteractor mLiveInteractor;
    private SortType mSortType = SortType.DATE_DES;
    private LiveCardRecyclerAdapter mAdapter;
    private List<Program> mProgramList;

    LivePresenterImpl(LiveView liveView){
        this.mLiveView = liveView;
        mLiveInteractor = new LiveInteractorImpl(mLiveView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        mAdapter = new LiveCardRecyclerAdapter(mLiveView.getDashboardActivity());
    }

    @Override
    public void onDestroy() {
        mLiveView = null;
    }

    @Override
    public void getBroadcasting(){
        mLiveInteractor.refreshServerConnection();
        mLiveInteractor.getServerConnection(new DashboardInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ServerConnection sc) {
                mLiveInteractor.getLiveList(new Callback<List<Program>>() {
                    @Override
                    public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                        mProgramList = response.body();
                        mAdapter.setLiveList(mProgramList);
                        mAdapter.notifyDataSetChanged();
                        mLiveView.setRecyclerView(mAdapter);//TODO: カラム数
                    }

                    @Override
                    public void onFailure(Call<List<Program>> call, Throwable t) {
                        mLiveView.removeRecyclerView();
                        mLiveView.showSnackBar(App.getContext().getString(R.string.failed_connect));
                    }
                });
            }

            @Override
            public void onNotFound() {
                mLiveView.removeRecyclerView();
                mLiveView.showSnackBar(App.getContext().getString(R.string.lets_register));//おいおい変更。カードにする。
            }
        });
    }

    @Override
    public void changeSortType(SortType type){
        mSortType = type;
        mAdapter.setLiveList(mProgramList);
        mAdapter.notifyDataSetChanged();
        mLiveView.setRecyclerView(mAdapter);
    }

    @Override
    public void searchWord(String word){
        ArrayList<Program> list = new ArrayList();

        for(Program r: mProgramList) {
            String t = Normalizer.normalize(r.getTitle(), Normalizer.Form.NFKC);
            String c = Normalizer.normalize(r.getChannel().getName(), Normalizer.Form.NFKC);
            if(r.getTitle().contains(word)
                    || r.getChannel().getName().contains(word)
                    || c.contains(word)
                    || t.contains(word)) list.add(r);
        }

        mAdapter.setLiveList(list);
        mAdapter.notifyDataSetChanged();
        mLiveView.setRecyclerView(mAdapter);
    }
}