package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private RecordedCardRecyclerAdapter mAdapter;
    private List<Recorded> mRecordedList;

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
                mRecordedInteractor.getRecordedList(new Callback<List<Recorded>>() {
                    @Override
                    public void onResponse(Call<List<Recorded>> call, Response<List<Recorded>> response) {
                        mRecordedList = response.body();
                        sort();
                        mAdapter.setRecorded(mRecordedList);
                        mAdapter.setListType(mListType);
                        mAdapter.notifyDataSetChanged();
                        mRecordedView.setRecyclerView(mAdapter, mListType);//TODO: カラム数
                    }

                    @Override
                    public void onFailure(Call<List<Recorded>> call, Throwable t) {
                        mRecordedView.removeRecyclerView();
                        mRecordedView.showSnackBar("サーバーへの接続に失敗しました");
                    }
                });
            }

            @Override
            public void onNotFound() {
                mRecordedView.removeRecyclerView();
                mRecordedView.showSnackBar("サーバーを登録しましょう");//おいおい変更。カードにする。
            }
        });
    }

    @Override
    public void changeListType(ListType type){
        mListType = type;
        getRecorded();
        mAdapter.setRecorded(mRecordedList);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void changeSortType(SortType type){
        mSortType = type;
        sort();
        mAdapter.setRecorded(mRecordedList);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void searchWord(String word){
        sort();
        ArrayList list = new ArrayList();

        for(Recorded r:mRecordedList) if(r.getTitle().contains(word)) list.add(r);

        mAdapter.setRecorded(list);
        mAdapter.notifyDataSetChanged();
        mRecordedView.setRecyclerView(mAdapter, mListType);
    }

    void sort(){
        switch (mSortType){
            case DATE_ASC:
                Collections.sort(mRecordedList, new DateComparator());
                break;
            case DATE_DES:
                Collections.sort(mRecordedList, new DateComparator());
                Collections.reverse(mRecordedList);
                break;
            case TITLE_ASC:
                Collections.sort(mRecordedList, new TitleComparator());
                break;
            case TITLE_DES:
                Collections.sort(mRecordedList, new TitleComparator());
                Collections.reverse(mRecordedList);
                break;
            case CATEGORY_ASC:
                Collections.sort(mRecordedList, new CategoryComparator());
                break;
            case CATEGORY_DES:
                Collections.sort(mRecordedList, new CategoryComparator());
                Collections.reverse(mRecordedList);
                break;
        }

    }
}

class DateComparator implements Comparator<Recorded> {
    @Override
    public int compare(Recorded r1, Recorded r2) {
        return r1.getStart() < r2.getStart() ? -1 : 1;
    }
}

class TitleComparator implements Comparator<Recorded> {
    @Override
    public int compare(Recorded r1, Recorded r2) {
        return r1.getTitle().compareTo(r2.getTitle());
    }
}

class CategoryComparator implements Comparator<Recorded> {
    @Override
    public int compare(Recorded r1, Recorded r2) {
        return getId(r1.getCategory()) < getId(r2.getCategory()) ? -1 : 1;
    }

    int getId(String category){
        switch (category){
            case "anime": return 0;
            case "information": return 1;
            case "news": return 2;
            case "sports": return 3;
            case "variety": return 4;
            case "drama": return 5;
            case "music": return 6;
            case "cinema": return 7;
            case "theater": return 8;
            case "documentary": return 9;
            case "hobby": return 10;
            case "welfare": return 11;
            case "etc": return 12;
            default: return 15;
        }
    }
}
