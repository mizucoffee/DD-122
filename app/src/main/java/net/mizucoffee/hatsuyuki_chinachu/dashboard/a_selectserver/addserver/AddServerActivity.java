package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivityAddServerBinding;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

public class AddServerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddServerBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_server);

        AddServerViewModel mAddServerVM = new AddServerViewModel(this);
        mBinding.setAddServerVM(mAddServerVM);

        mAddServerVM.finished.subscribe(programItems -> finish() );

        setSupportActionBar(mBinding.toolbar);

        if (getIntent().getStringExtra("data") != null) {
            ServerConnection sc = new Gson().fromJson(getIntent().getStringExtra("data"),ServerConnection.class);
            mAddServerVM.setData(sc,getIntent().getIntExtra("position",0));
        }
    }
}