package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
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

        mRecyclerView = new RecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ServerConnection> connections = new ArrayList<>();
        ServerConnection con = new ServerConnection();
        con.setHost("host");
        con.setName("server");
        con.setPort("10472");
        connections.add(con);


        SelectServerCardRecyclerAdapter adapter = new SelectServerCardRecyclerAdapter(this, connections, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("FUBUKI","Clicked");
            }
        });


        mRecyclerView.setAdapter(adapter);

//        mPresenter.getList();
    }

    @Override
    public void setRecyclerView(ArrayList<ServerConnection> connections){
        Log.i("FUBUKI", "setRecycler");
        mRecyclerView.setAdapter(new SelectServerCardRecyclerAdapter(this, connections, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("FUBUKI","Clicked");
            }
        }));
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

    @Override
    public SharedPreferences getActivitySharedPreferences(String name , int mode){
        return getSharedPreferences(name,mode);
    }
}
