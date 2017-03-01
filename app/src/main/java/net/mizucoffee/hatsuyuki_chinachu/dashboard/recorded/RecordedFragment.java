package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.adapter.RecordedCard1ColumnRecyclerAdapter;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.adapter.RecordedCard2ColumnRecyclerAdapter;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.adapter.RecordedCardListRecyclerAdapter;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordedFragment extends Fragment implements RecordedView{

    private RecordedPresenter mPresenter;
    private RecyclerView.Adapter mAdapter;

    @BindView(R.id.recycler)
    public RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_recorded, container, false);
        Shirayuki.initFragment(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new RecordedPresenterImpl(this);
        mRecyclerView.setHasFixedSize(true);

        mPresenter.getRecorded();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.recorded, menu);
        menu.findItem(R.id.menu_sort).setVisible(true);
        menu.findItem(R.id.menu_list).setVisible(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case R.id.menu_sort:
                break;

            case R.id.menu_list:
                PopupMenu popup = new PopupMenu(getActivity(), ButterKnife.findById(getActivity(),R.id.menu_list));
                popup.getMenuInflater().inflate(R.menu.recorded_listtype_popup_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener((menu) -> {
                    ListType listType = menu.getItemId() == R.id.column1 ? ListType.CARD_COLUMN1 : menu.getItemId() == R.id.column2 ? ListType.CARD_COLUMN2 : ListType.LIST;
                    mPresenter.changeSort(listType);
                    return super.onOptionsItemSelected(menu);
                });
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRecorded();
    }

    @Override
    public void showSnackbar(String text){
        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    private DashboardActivity activity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (DashboardActivity)context;
        }

    }

    @Override
    public DashboardActivity getDashboardActivity(){
        return activity;
    }

    @Override
    public void setRecyclerView(List<Recorded> recorded,ListType listType){
        switch (listType){
            case CARD_COLUMN1:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                mAdapter = new RecordedCard1ColumnRecyclerAdapter(getDashboardActivity(), recorded);
                break;
            case CARD_COLUMN2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mAdapter = new RecordedCard2ColumnRecyclerAdapter(getDashboardActivity(), recorded);
                break;
            case LIST:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                mAdapter = new RecordedCardListRecyclerAdapter(getDashboardActivity(), recorded);
                break;
        }

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void removeRecyclerView(){
        mAdapter = null;
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public SharedPreferences getActivitySharedPreferences(String name, int mode){
        return getActivity().getSharedPreferences(name,mode);
    }
}
