package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.CategoryComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.DateComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.TitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordedPresenterImpl implements RecordedPresenter {

    private RecordedView mRecordedView;
    private RecordedInteractor mRecordedInteractor;
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private RecordedCardRecyclerAdapter mAdapter;
    private List<Program> mProgramList;

    RecordedPresenterImpl(RecordedView recordedView){
        this.mRecordedView = recordedView;
        mRecordedInteractor = new RecordedInteractorImpl(mRecordedView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        mAdapter = new RecordedCardRecyclerAdapter(mRecordedView.getDashboardActivity());
    }

    @Override
    public void onDestroy() {
        mRecordedView = null;
    }

    @Override
    public void getRecorded(){
        mRecordedInteractor.refreshServerConnection();
        mRecordedInteractor.getServerConnection(new DashboardInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ServerConnection sc) {
                mRecordedInteractor.getRecordedList(new Callback<List<Program>>() {
                    @Override
                    public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                        mProgramList = response.body();
                        sort();
                        mAdapter.setRecorded(mProgramList);
                        mAdapter.setListType(mListType);
                        mAdapter.notifyDataSetChanged();
                        mRecordedView.setRecyclerView(mAdapter, mListType);//TODO: カラム数
                    }

                    @Override
                    public void onFailure(Call<List<Program>> call, Throwable t) {
                        mRecordedView.removeRecyclerView();
                        mRecordedView.showSnackBar(App.getContext().getString(R.string.failed_connect));
                    }
                });
            }

            @Override
            public void onNotFound() {
                mRecordedView.removeRecyclerView();
                mRecordedView.showSnackBar(App.getContext().getString(R.string.lets_register));//おいおい変更。カードにする。
            }
        });
    }

    @Override
    public void changeListType(ListType type){
        mListType = type;
        getRecorded();
        mAdapter.setRecorded(mProgramList);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void changeSortType(SortType type){
        mSortType = type;
        sort();
        mAdapter.setRecorded(mProgramList);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void searchWord(String word){
        sort();
        ArrayList list = new ArrayList();

        for(Program r: mProgramList) if(r.getTitle().contains(word)) list.add(r);

        mAdapter.setRecorded(list);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    void sort(){
        switch (mSortType){
            case DATE_ASC:
                Collections.sort(mProgramList, new DateComparator());
                break;
            case DATE_DES:
                Collections.sort(mProgramList, new DateComparator());
                Collections.reverse(mProgramList);
                break;
            case TITLE_ASC:
                Collections.sort(mProgramList, new TitleComparator());
                break;
            case TITLE_DES:
                Collections.sort(mProgramList, new TitleComparator());
                Collections.reverse(mProgramList);
                break;
            case CATEGORY_ASC:
                Collections.sort(mProgramList, new CategoryComparator());
                break;
            case CATEGORY_DES:
                Collections.sort(mProgramList, new CategoryComparator());
                Collections.reverse(mProgramList);
                break;
        }

    }
}