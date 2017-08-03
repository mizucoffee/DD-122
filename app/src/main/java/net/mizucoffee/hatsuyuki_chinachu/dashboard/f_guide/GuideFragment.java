package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.databinding.FragmentGuideBinding;

import static butterknife.ButterKnife.findById;

public class GuideFragment extends Fragment{

    private GuideViewModel mGuideVM;
    private SearchView mSearchView;
    private FragmentGuideBinding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_guide, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).setSupportActionBar(findById(getActivity(),R.id.toolbar));

        mBinding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mBinding.tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        mBinding.tabs.setupWithViewPager(mBinding.pager);

//        mBinding.pager

//        mBinding.tabs.addTab(mBinding.tabs.newTab().setText("tab 1"));

        mGuideVM = new GuideViewModel(this);
        mBinding.setGuideVM(mGuideVM);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGuideVM.reload();
    }
}
