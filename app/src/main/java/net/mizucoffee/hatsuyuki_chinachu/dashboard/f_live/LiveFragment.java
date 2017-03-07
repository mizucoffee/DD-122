package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;

public class LiveFragment extends Fragment implements LiveView {

    private LivePresenter mPresenter;
    @BindView(R.id.recycler)
    public RecyclerView mRecyclerView;

    private SearchView mSearchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_program_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Shirayuki.initFragment(this,view);

        mPresenter = new LivePresenterImpl(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mPresenter.getBroadcasting();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.program_list, menu);
        menu.findItem(R.id.menu_sort).setVisible(true);
        menu.findItem(R.id.menu_list).setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.search_menu_search_view);

        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public SharedPreferences getActivitySharedPreferences(String name, int mode){
        return getActivity().getSharedPreferences(name,mode);
    }

    @Override
    public void setRecyclerView(LiveCardRecyclerAdapter adapter){
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

    @Override
    public void showSnackBar(String text){
        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }
}
