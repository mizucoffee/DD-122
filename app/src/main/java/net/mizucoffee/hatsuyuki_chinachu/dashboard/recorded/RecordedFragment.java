package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.List;

import butterknife.BindView;

public class RecordedFragment extends Fragment implements RecordedView{

    private RecordedPresenter mPresenter;
    private RecordedCardRecyclerAdapter mAdapter;

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

        mPresenter.getRecorded(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRecorded(getActivity());
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
    public void setRecyclerView(List<Recorded> recorded,int column){
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), column));
        mAdapter = new RecordedCardRecyclerAdapter(getDashboardActivity(), recorded, column);
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
