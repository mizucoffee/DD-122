package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import net.mizucoffee.hatsuyuki_chinachu.App;
import net.mizucoffee.hatsuyuki_chinachu.R;
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

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DownloadedViewModel {

    public final ObservableField<DownloadedCardRecyclerAdapter> list = new ObservableField<>();
    public final ObservableField<Integer> column = new ObservableField<>();

    private DownloadedModel mDownloadedModel;
    private DownloadedCardRecyclerAdapter mAdapter;
    private ListType mListType = ListType.CARD_COLUMN1;
    private SortType mSortType = SortType.DATE_DES;
    private List<ProgramItem> mDownloadedList;

    private final PublishSubject<String> snackSubject = PublishSubject.create();
    final Observable<String> snack = (Observable<String>) snackSubject;

    DownloadedViewModel(DownloadedFragment downloadedFragment){
        mDownloadedModel = new DownloadedModel(downloadedFragment.getActivity().getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        mAdapter = new DownloadedCardRecyclerAdapter(downloadedFragment.getActivity());
        subscribe();
    }

    private void subscribe(){
        mDownloadedModel.programItems.subscribe(programItems -> {
            Shirayuki.log("success");
            mDownloadedList = programItems;
            setAdapterToRecyclerView(mDownloadedList);
        });
    }

    private void setAdapterToRecyclerView(List<ProgramItem> programList){
        List<ProgramItem> programItems = sort(programList);
        if(programItems.isEmpty()) snackSubject.onNext(App.getContext().getString(R.string.lets_download));
        mAdapter.setDownloaded(programItems);
        mAdapter.setListType(mListType);

        mAdapter.notifyDataSetChanged();
        list.set(mAdapter);
        column.set(mAdapter.getListType() == ListType.CARD_COLUMN2 ? 2 : 1);
    }

    void reload(){
        mDownloadedModel.getDownloadedList();
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
        setAdapterToRecyclerView(mDownloadedList);
        return false;
    };

    PopupMenu.OnMenuItemClickListener listMenuListener = (menu) -> {
        mListType = menu.getItemId() == R.id.column1 ? ListType.CARD_COLUMN1 : menu.getItemId() == R.id.column2 ? ListType.CARD_COLUMN2 : ListType.LIST;
        setAdapterToRecyclerView(mDownloadedList);
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
            for(ProgramItem r: mDownloadedList) if(r.getTitle().contains(word)) list.add(r);
            setAdapterToRecyclerView(list);
            return false;
        }
    };

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("adapterList")
    public static void setAdapterList(RecyclerView rv, DownloadedCardRecyclerAdapter adapter) {
        rv.setAdapter(adapter);
    }

    @BindingAdapter("gridColumn")
    public static void setGridColumn(RecyclerView rv, int i) {
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(), i));
    }
}






