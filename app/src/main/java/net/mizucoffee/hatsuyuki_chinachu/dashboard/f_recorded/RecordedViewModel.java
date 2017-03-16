package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.tools.CategoryComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.DateComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;
import net.mizucoffee.hatsuyuki_chinachu.tools.TitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordedViewModel {

    public final ObservableField<RecordedCardRecyclerAdapter> list = new ObservableField<>();

    private RecordedModel mRecordedModel;
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private RecordedCardRecyclerAdapter mAdapter;
    private List<ProgramItem> mProgramList;

    RecordedViewModel(RecordedFragment fragment){
        mRecordedModel = new RecordedModel(fragment.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        subscribe();
        mAdapter = new RecordedCardRecyclerAdapter(fragment.getDashboardActivity());
    }

    private void subscribe(){
        mRecordedModel.programItems.subscribe(programItems -> {
            Shirayuki.log("success");
            mProgramList = programItems;
            setAdapterToRecyclerView(mProgramList);
        });
        mRecordedModel.error.subscribe(i -> {
            if(i == RecordedModel.NOT_FOUND){
                Shirayuki.log("error: NotFound");
            }else if(i == RecordedModel.FAILURE){
                Shirayuki.log("error: FAILURE");
            }
//            mRecordedView.removeRecyclerView();
//            mRecordedView.showSnackBar(App.getContext().getString(R.string.failed_connect));
        });
    }

    private void setAdapterToRecyclerView(List<ProgramItem> programList){
        List<ProgramItem> programItems = sort(programList);
        mAdapter.setRecorded(programItems);
        mAdapter.setListType(mListType);

        mAdapter.notifyDataSetChanged();
        list.set(mAdapter);
    }

    void getRecorded(){
        mRecordedModel.getRecordedList();
    }

    void changeListType(ListType type){
        mListType = type;
        setAdapterToRecyclerView(mProgramList);
    }

    void changeSortType(SortType type){
        mSortType = type;
        setAdapterToRecyclerView(mProgramList);
    }

    void searchWord(String word){
        ArrayList<ProgramItem> list = new ArrayList<>();
        for(ProgramItem r: mProgramList) if(r.getTitle().contains(word)) list.add(r);
        setAdapterToRecyclerView(list);
    }

    private List<ProgramItem> sort(List<ProgramItem> items){
        switch (mSortType){
            case DATE_ASC:
                Collections.sort(items, new DateComparator());
                break;
            case DATE_DES:
                Collections.sort(items, new DateComparator());
                Collections.reverse(items);
                break;
            case TITLE_ASC:
                Collections.sort(items, new TitleComparator());
                break;
            case TITLE_DES:
                Collections.sort(items, new TitleComparator());
                Collections.reverse(items);
                break;
            case CATEGORY_ASC:
                Collections.sort(items, new CategoryComparator());
                break;
            case CATEGORY_DES:
                Collections.sort(items, new CategoryComparator());
                Collections.reverse(items);
                break;
        }
        return items;
    }

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, RecordedCardRecyclerAdapter adapter) {
        if (adapter != null) {
            Shirayuki.log(adapter.getListType().name());
            rv.setLayoutManager(new GridLayoutManager(rv.getContext(), adapter.getListType() == ListType.CARD_COLUMN2 ? 2 : 1));
        }
        rv.setAdapter(adapter);
    }
}