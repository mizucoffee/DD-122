package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.RecordedFragment;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.SelectServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;

import static butterknife.ButterKnife.findById;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DashboardView {

    private GuideFragment mGuideFragment = new GuideFragment();
    private LiveFragment mLiveFragment = new LiveFragment();
    private RecordedFragment mRecordedFragment = new RecordedFragment();
    private TimerFragment mTimerFragment = new TimerFragment();

    private DashboardPresenter mPresenter;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    private ServerConnection serverConnection;
    //TODO: onactivityresultでip自動更新

    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Shirayuki.initActivity(this);

        mPresenter = new DashboardPresenterImpl(this);
        mPresenter.refreshConnection();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, findById(this, R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        findById(mNavigationView.getHeaderView(0), R.id.nav_button).setOnClickListener(v -> mPresenter.intentSelectServer());

        if(!isFirst){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, mRecordedFragment);
            transaction.commit();
        }

    }

    @Override
    public void setFirst(boolean b){
        isFirst = b;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.refreshConnection();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void intentSelectServer(boolean first) {
        startActivity(new Intent(this, SelectServerActivity.class).putExtra("first",first));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.nav_live:
                transaction.replace(R.id.container, mLiveFragment);
                break;
            case R.id.nav_guide:
                transaction.replace(R.id.container, mGuideFragment);
                break;
            case R.id.nav_recorded:
                transaction.replace(R.id.container, mRecordedFragment);
                break;
            case R.id.nav_timers:
                transaction.replace(R.id.container, mTimerFragment);
                break;
        }

        transaction.commit();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public SharedPreferences getActivitySharedPreferences(String name, int mode){
        return getSharedPreferences(name,mode);
    }

    @Override
    public void setServerConnection(ServerConnection sc) {
        serverConnection = sc;
    }

    @Override
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    @Override
    public void activityFinish(){
        finish();
    }
}