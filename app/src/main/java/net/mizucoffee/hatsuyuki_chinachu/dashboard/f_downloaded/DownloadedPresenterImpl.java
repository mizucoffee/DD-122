package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.tools.CategoryComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.DateComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.TitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownloadedPresenterImpl implements DownloadedPresenter {

    private DownloadedView mDownloadedView;
    private DownloadedInteractor mDownloadedInteractor;
    private DownloadedCardRecyclerAdapter mAdapter;
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private List<Program> mDownloadedList;

    DownloadedPresenterImpl(DownloadedView downloadedView){
        this.mDownloadedView = downloadedView;
        mDownloadedInteractor = new DownloadedInteractorImpl(mDownloadedView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        mAdapter = new DownloadedCardRecyclerAdapter(mDownloadedView.getDashboardActivity());
    }

    @Override
    public void onDestroy() {
        mDownloadedView = null;
    }

    @Override
    public void getDownloaded(){
        List<Program> programList = mDownloadedInteractor.getDownloadedList();
        if(programList == null) {
            mDownloadedView.showSnackBar(App.getContext().getString(R.string.lets_download));
            // DLしてみましょうを入れるか
            return;
        }

        mDownloadedList = programList;
        sort();
        mAdapter.setRecorded(mDownloadedList);
        mAdapter.setListType(mListType);
        mAdapter.notifyDataSetChanged();
        mDownloadedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void changeListType(ListType type){
        mListType = type;
        getDownloaded();
        mAdapter.setRecorded(mDownloadedList);
        mAdapter.notifyDataSetChanged();
        mDownloadedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void changeSortType(SortType type){
        mSortType = type;
        sort();
        mAdapter.setRecorded(mDownloadedList);
        mAdapter.notifyDataSetChanged();
        mDownloadedView.setRecyclerView(mAdapter, mListType);
    }

    @Override
    public void searchWord(String word){
        sort();
        ArrayList list = new ArrayList<Program>();

        for(Program r:mDownloadedList) if(r.getTitle().contains(word)) list.add(r);

        mAdapter.setRecorded(list);
        mAdapter.notifyDataSetChanged();
        mDownloadedView.setRecyclerView(mAdapter, mListType);
    }

    private void sort(){
        switch (mSortType){
            case DATE_ASC:
                Collections.sort(mDownloadedList, new DateComparator());
                break;
            case DATE_DES:
                Collections.sort(mDownloadedList, new DateComparator());
                Collections.reverse(mDownloadedList);
                break;
            case TITLE_ASC:
                Collections.sort(mDownloadedList, new TitleComparator());
                break;
            case TITLE_DES:
                Collections.sort(mDownloadedList, new TitleComparator());
                Collections.reverse(mDownloadedList);
                break;
            case CATEGORY_ASC:
                Collections.sort(mDownloadedList, new CategoryComparator());
                break;
            case CATEGORY_DES:
                Collections.sort(mDownloadedList, new CategoryComparator());
                Collections.reverse(mDownloadedList);
                break;
        }

    }
}






