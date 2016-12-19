package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardPresenter;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardPresenterImpl;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver.AddServerActivity;

public class SelectServerActivity extends AppCompatActivity implements SelectServerView{

    private SelectServerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_server);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new SelectServerPresenterImpl(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.intentAdd();
            }
        });

        mPresenter.getList();
    }

    @Override
    public void intentAdd() {
        startActivity(new Intent(this, AddServerActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
