package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.databinding.FragmentChannelBinding;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.ArrayList;
import java.util.List;

public class ChannelFragment extends Fragment {

    FragmentChannelBinding mBinding;
    ArrayList<List<ProgramItem>> list;

    public ChannelFragment() {}

    public static ChannelFragment newInstance(ArrayList<List<ProgramItem>> array) {
        Bundle args = new Bundle();
        args.putString("list", new Gson().toJson(array));
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String c = getArguments().getString("list", "");
        if(c.isEmpty()) return null;

        list = new Gson().fromJson(c, new TypeToken<ArrayList<List<ProgramItem>>>(){}.getType());;

        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_channel, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.scrollView.setAdapter(new ProgramAdapter(list));
    }
}
