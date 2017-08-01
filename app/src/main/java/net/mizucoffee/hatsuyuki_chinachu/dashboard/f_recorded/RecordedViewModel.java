package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.CategoryComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.DateComparator;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;
import net.mizucoffee.hatsuyuki_chinachu.tools.TitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordedViewModel {

    public final ObservableField<RecordedCardRecyclerAdapter> list = new ObservableField<>();
    public final ObservableField<Integer> column = new ObservableField<>();

    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private RecordedCardRecyclerAdapter mAdapter;
    private List<ProgramItem> mProgramList;

    private ServerConnection currentSc;

    RecordedViewModel(RecordedFragment fragment){
        subscribe();
        DataModel.Companion.getInstance().getCurrentServerConnection();
        mAdapter = new RecordedCardRecyclerAdapter((DashboardActivity)fragment.getActivity(), currentSc.getAddress());
        column.set(1); //TODO: デフォルト変更
    }

    private void subscribe(){
        ChinachuModel.INSTANCE.getProgramItems().subscribe(programItems -> {
            Shirayuki.log("success");
            mProgramList = programItems;
            setAdapterToRecyclerView(mProgramList);
        });
        DataModel.Companion.getInstance().getCurrentServerConnection.subscribe(sc -> {
            currentSc = sc;
            ChinachuModel.INSTANCE.getRecordedList(sc.getAddress());
        });
    }

    private void setAdapterToRecyclerView(List<ProgramItem> programList){
        List<ProgramItem> programItems = sort(programList);
        mAdapter.setRecorded(programItems);
        mAdapter.setListType(mListType);

        mAdapter.notifyDataSetChanged();
        list.set(mAdapter);
        column.set(mAdapter.getListType() == ListType.CARD_COLUMN2 ? 2 : 1);
    }

    void reload(){
        DataModel.Companion.getInstance().getCurrentServerConnection();
    }

    private List<ProgramItem> sort(List<ProgramItem> items){
        switch (mSortType){
            case DATE_ASC: Collections.sort(items, new DateComparator()); break;
            case DATE_DES: Collections.sort(items, new DateComparator()); Collections.reverse(items); break;
            case TITLE_ASC: Collections.sort(items, new TitleComparator()); break;
            case TITLE_DES: Collections.sort(items, new TitleComparator()); Collections.reverse(items); break;
            case CATEGORY_ASC: Collections.sort(items, new CategoryComparator()); break;
            case CATEGORY_DES: Collections.sort(items, new CategoryComparator()); Collections.reverse(items); break;
        }
        return items;
    }

    PopupMenu.OnMenuItemClickListener sortMenuListener = (menu) -> {
        mSortType = SortType.DATE_DES;
        switch (menu.getItemId()) {
            case R.id.dateasc: mSortType = SortType.DATE_ASC; break;
            case R.id.datedes: mSortType = SortType.DATE_DES; break;
            case R.id.titleasc: mSortType = SortType.TITLE_ASC; break;
            case R.id.titledes: mSortType = SortType.TITLE_DES; break;
            case R.id.catasc: mSortType = SortType.CATEGORY_ASC; break;
            case R.id.catdes: mSortType = SortType.CATEGORY_DES; break;
        }
        setAdapterToRecyclerView(mProgramList);
        return false;
    };

    PopupMenu.OnMenuItemClickListener listMenuListener = (menu) -> {
        mListType = menu.getItemId() == R.id.column1 ? ListType.CARD_COLUMN1 : menu.getItemId() == R.id.column2 ? ListType.CARD_COLUMN2 : ListType.LIST;
        setAdapterToRecyclerView(mProgramList);
        return false;
    };

    SearchView.OnQueryTextListener onSearch = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String word) {
            ArrayList<ProgramItem> list = new ArrayList<>();
            for(ProgramItem r: mProgramList) if(r.getTitle().contains(word)) list.add(r);
            setAdapterToRecyclerView(list);
            return false;
        }
    };

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, RecordedCardRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }

    @BindingAdapter("gridColumn")
    public static void setGridColumn(RecyclerView rv, int i) {
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(), i));
    }
}