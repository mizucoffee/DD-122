package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.databinding.FragmentChannelBinding;
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel;

public class ChannelFragment extends Fragment {

    FragmentChannelBinding mBinding;
    ScheduleModel sm;

    public ChannelFragment() {}

    public static ChannelFragment newInstance(ScheduleModel channelSm) {
        Bundle args = new Bundle();
        args.putString("channel", new Gson().toJson(channelSm));
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String c = getArguments().getString("channel", "");
        if(c.isEmpty()) return null;

        sm = new Gson().fromJson(c, ScheduleModel.class);

        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_channel, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.scrollView.setAdapter(new ProgramAdapter(sm));
    }
}
