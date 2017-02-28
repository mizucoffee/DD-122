package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver.AddServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectServerActivity extends AppCompatActivity implements SelectServerView{

    private SelectServerPresenter mPresenter;
    private SelectServerCardRecyclerAdapter mAdapter;

    @BindView(R.id.recycler)
    public RecyclerView mRecyclerView;

    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_server);
        Shirayuki.initActivity(this);

        isFirst = getIntent().getBooleanExtra("first",false);

        mPresenter = new SelectServerPresenterImpl(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> mPresenter.intentAdd());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getList();

        if(isFirst){
            if(mAdapter.getItemCount() == 1)
                Snackbar.make(findViewById(R.id.recycler), "まずはサーバー登録をしましょう", Snackbar.LENGTH_SHORT)
                        .setAction("NEW SERVER", (v) -> mPresenter.intentAdd())
                        .show();
            else
                Snackbar.make(findViewById(R.id.recycler), "サーバー選択をしましょう", Snackbar.LENGTH_SHORT)
                        .show();
        }
    }

    @Override
    public void setRecyclerView(ArrayList<ServerConnection> connections){
        mAdapter = new SelectServerCardRecyclerAdapter(this, connections,mPresenter, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void intentAdd() {
        startActivityForResult(new Intent(this, AddServerActivity.class),0);
    }

    @Override
    public void intentEdit(ServerConnection sc,int position) {
        Intent i = new Intent(this, AddServerActivity.class);
        i.putExtra("data",new Gson().toJson(sc));
        i.putExtra("position",position);
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){mPresenter.getList();}
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

    @Override
    public void onBackPressed() {
        if(mAdapter.getItemCount() == 0) super.onBackPressed();

        if (isFirst) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }else
            super.onBackPressed();
    }

    @Override
    public void finishActivity() {
        if (isFirst) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }else
            super.onBackPressed();
    }
}
