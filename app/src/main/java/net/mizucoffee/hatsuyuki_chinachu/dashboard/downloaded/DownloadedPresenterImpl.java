package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DownloadedPresenterImpl implements DownloadedPresenter {

    private DownloadedView mDownloadedView;
    private DownloadedInteractor mDownloadedInteractor;
    private DownloadedCardRecyclerAdapter mAdapter;
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private List<Recorded> mDownloadedList;

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
        List<Recorded> recorded = mDownloadedInteractor.getDownloadedList();
        if(recorded == null) return;// DLしてみましょうを入れるか

        mDownloadedList = recorded;
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
        ArrayList list = new ArrayList();

        for(Recorded r:mDownloadedList) if(r.getTitle().contains(word)) list.add(r);

        mAdapter.setRecorded(list);
        mAdapter.notifyDataSetChanged();
        mDownloadedView.setRecyclerView(mAdapter, mListType);
    }

    void sort(){
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
