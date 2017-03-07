package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

        mPresenter.getBroadcasting();
        setHasOptionsMenu(true);
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
    public void setRecyclerView(LiveCardRecyclerAdapter adapter, ListType listType){
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

    @Override
    public void showSnackBar(String text){
        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }
}
