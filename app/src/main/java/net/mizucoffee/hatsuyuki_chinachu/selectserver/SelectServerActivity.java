package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver.AddServerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectServerActivity extends AppCompatActivity implements SelectServerView{

    private SelectServerPresenter mPresenter;

    @BindView(R.id.recycler)
//    public SelectServerCardRecyclerView recyclerView;
    public RecyclerView mRecyclerView;
    private SelectServerCardRecyclerAdapter adapter;

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

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getList();
    }

    @Override
    public void setRecyclerView(ArrayList<ServerConnection> connections){
        Log.i("FUBUKI", "setRecycler");
        adapter = new SelectServerCardRecyclerAdapter(this, connections,mPresenter, mPresenter);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void intentAdd() {
        startActivityForResult(new Intent(this, AddServerActivity.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                mPresenter.getList();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public SharedPreferences getActivitySharedPreferences(String name , int mode){
        return getSharedPreferences(name,mode);
    }

    @Override
    public void showAlertDialog(String title,String message,DialogInterface.OnClickListener onClickListener){
        new AlertDialog.Builder(SelectServerActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",onClickListener)
                .setNegativeButton("Cancel", null)
                .show();
    }
}
