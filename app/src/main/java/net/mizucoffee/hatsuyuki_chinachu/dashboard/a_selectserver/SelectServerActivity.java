package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver.AddServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivitySelectServerBinding;

public class SelectServerActivity extends AppCompatActivity{

    private SelectServerViewModel mSelectServerVM;
    private ActivitySelectServerBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_select_server);

        mSelectServerVM = new SelectServerViewModel(this);
        mBinding.setSelectServerVM(mSelectServerVM);
        setSupportActionBar(mBinding.toolbar);

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectServerVM.reload();
    }

    private void subscribe() {
        mSelectServerVM.action.subscribe(action -> {
            switch (action.getAction()){
                case SNACK_REGISTER:
                    Snackbar.make(findViewById(R.id.recycler), getString(R.string.lets_register), Snackbar.LENGTH_LONG).show();
                    break;
                case SNACK_SELECT:
                    Snackbar.make(findViewById(R.id.recycler), getString(R.string.lets_select_server), Snackbar.LENGTH_LONG).show();
                    break;
                case FINISH:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case INTENT_ADD:
                    startActivity(new Intent(this, AddServerActivity.class));
                    break;
                case INTENT_EDIT:
                    Intent i = new Intent(this, AddServerActivity.class);
                    i.putExtra("data",new Gson().toJson(action.getSc()));
                    i.putExtra("position",action.getPosition());
                    startActivity(i);
                    break;
                case ALERT:
                    new AlertDialog.Builder(SelectServerActivity.this)
                            .setTitle(getString(R.string.confirm))
                            .setMessage(getString(R.string.confirm_delete))
                            .setPositiveButton(getString(R.string.ok),action.getOnClickListener())
                            .setNegativeButton(getString(R.string.cancel), null)
                            .show();
                    break;
            }
        });
    }
}
