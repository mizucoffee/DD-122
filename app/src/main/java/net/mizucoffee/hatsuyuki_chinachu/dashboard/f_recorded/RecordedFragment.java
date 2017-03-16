package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.databinding.FragmentRecordedBinding;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;

import static butterknife.ButterKnife.findById;

public class RecordedFragment extends Fragment{

    private RecordedViewModel mRecordedVM;
    private SearchView mSearchView;
    private FragmentRecordedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recorded, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setSupportActionBar(findById(activity,R.id.toolbar));

        mRecordedVM = new RecordedViewModel(this);
        binding.setRecordedVM(mRecordedVM);

        binding.recycler.setHasFixedSize(true);

        mRecordedVM.getRecorded();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.program_list, menu);

        MenuItem menuItem = menu.findItem(R.id.search_menu_search_view);
        mSearchView = (SearchView)menuItem.getActionView();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mRecordedVM.searchWord(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_sort:
                PopupMenu popup = new PopupMenu(getActivity(), findById(getActivity(),R.id.menu_sort));
                popup.getMenuInflater().inflate(R.menu.program_sorttype_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener((menu) -> {
                    SortType sortType = SortType.DATE_DES;
                    switch (menu.getItemId()){
                        case R.id.dateasc:  sortType = SortType.DATE_ASC; break;
                        case R.id.datedes:  sortType = SortType.DATE_DES; break;
                        case R.id.titleasc: sortType = SortType.TITLE_ASC; break;
                        case R.id.titledes: sortType = SortType.TITLE_DES; break;
                        case R.id.catasc:   sortType = SortType.CATEGORY_ASC; break;
                        case R.id.catdes:   sortType = SortType.CATEGORY_DES; break;
                    }
                    mRecordedVM.changeSortType(sortType);
                    return super.onOptionsItemSelected(menu);
                });
                popup.show();
                break;
            case R.id.menu_list:
                PopupMenu popup2 = new PopupMenu(getActivity(), findById(getActivity(),R.id.menu_list));
                popup2.getMenuInflater().inflate(R.menu.program_listtype_popup_menu, popup2.getMenu());
                popup2.setOnMenuItemClickListener((menu) -> {
                    ListType listType = menu.getItemId() == R.id.column1 ? ListType.CARD_COLUMN1 : menu.getItemId() == R.id.column2 ? ListType.CARD_COLUMN2 : ListType.LIST;
                    mRecordedVM.changeListType(listType);
                    return super.onOptionsItemSelected(menu);
                });
                popup2.show();
                break;
        }
        return true;
    }

    private DashboardActivity activity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (DashboardActivity)context;
    }

    public DashboardActivity getDashboardActivity(){
        return activity;
    }

    public void reload(){
        mRecordedVM.getRecorded();
    }

    public boolean isSearchBarVisible(){
        return !mSearchView.isIconified();
    }

    public void setSearchBarInVisible(){
        mSearchView.setIconified(true);
    }

    public SharedPreferences getActivitySharedPreferences(String name, int mode){
        return getActivity().getSharedPreferences(name,mode);
    }
}
