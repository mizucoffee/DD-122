package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.adapter.RecordedCardRecyclerAdapter;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.SortType;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordedFragment extends Fragment implements RecordedView{

    private RecordedPresenter mPresenter;


    @BindView(R.id.recycler)
    public RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_recorded, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Shirayuki.initFragment(this,view);

        mPresenter = new RecordedPresenterImpl(this);
        mRecyclerView.setHasFixedSize(true);

        mPresenter.getRecorded();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.recorded, menu);
        menu.findItem(R.id.menu_sort).setVisible(true);
        menu.findItem(R.id.menu_list).setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.search_menu_search_view);

        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.searchWord(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case R.id.menu_sort:
                PopupMenu popup = new PopupMenu(getActivity(), ButterKnife.findById(getActivity(),R.id.menu_sort));
                popup.getMenuInflater().inflate(R.menu.recorded_sorttype_popup_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener((menu) -> {
                    SortType sortType = SortType.DATE_DES;
                    switch (menu.getItemId()){
                        case R.id.dateasc: sortType = SortType.DATE_ASC; break;
                        case R.id.datedes: sortType = SortType.DATE_DES; break;
                        case R.id.titleasc: sortType = SortType.TITLE_ASC; break;
                        case R.id.titledes: sortType = SortType.TITLE_DES; break;
                        case R.id.catasc: sortType = SortType.CATEGORY_ASC; break;
                        case R.id.catdes: sortType = SortType.CATEGORY_DES; break;
                    }
                    mPresenter.changeSortType(sortType);
                    return super.onOptionsItemSelected(menu);
                });
                break;
            case R.id.menu_list:
                PopupMenu popup2 = new PopupMenu(getActivity(), ButterKnife.findById(getActivity(),R.id.menu_list));
                popup2.getMenuInflater().inflate(R.menu.recorded_listtype_popup_menu, popup2.getMenu());
                popup2.show();
                popup2.setOnMenuItemClickListener((menu) -> {
                    ListType listType = menu.getItemId() == R.id.column1 ? ListType.CARD_COLUMN1 : menu.getItemId() == R.id.column2 ? ListType.CARD_COLUMN2 : ListType.LIST;
                    mPresenter.changeListType(listType);
                    return super.onOptionsItemSelected(menu);
                });
                break;
        }
        return true;
    }

    @Override
    public void showSnackBar(String text){
        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    private DashboardActivity activity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (DashboardActivity)context;
    }

    @Override
    public DashboardActivity getDashboardActivity(){
        return activity;
    }

    @Override
    public void setRecyclerView(RecordedCardRecyclerAdapter adapter,ListType listType){
        switch (listType) {
            case CARD_COLUMN1:
            case LIST:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                break;
            case CARD_COLUMN2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;
        }

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void removeRecyclerView(){
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void reload(){
        mPresenter.getRecorded();
    }

    @Override
    public SharedPreferences getActivitySharedPreferences(String name, int mode){
        return getActivity().getSharedPreferences(name,mode);
    }
}
