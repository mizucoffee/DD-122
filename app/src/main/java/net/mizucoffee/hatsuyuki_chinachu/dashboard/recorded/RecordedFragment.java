package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.getRecorded();
    }

    @Override
    public void setRecyclerView(List<Recorded> recorded){
        mAdapter = new RecordedCardRecyclerAdapter(getActivity(), recorded);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
