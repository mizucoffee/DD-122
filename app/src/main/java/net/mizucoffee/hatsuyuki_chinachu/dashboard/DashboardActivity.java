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
import android.widget.TextView;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded.DownloadedFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.RecordedFragment;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.SelectServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.settings.SettingsActivity;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;

import static butterknife.ButterKnife.findById;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DashboardView {

    private GuideFragment mGuideFragment = new GuideFragment();
    private LiveFragment mLiveFragment = new LiveFragment();
    private RecordedFragment mRecordedFragment = new RecordedFragment();
    private TimerFragment mTimerFragment = new TimerFragment();
    private DownloadedFragment mDownloadedFragment = new DownloadedFragment();

    private DashboardPresenter mPresenter;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    int currentMenuId;

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

        mNavigationView.setCheckedItem(R.id.nav_recorded);
        mNavigationView.setNavigationItemSelectedListener(this);
        findById(mNavigationView.getHeaderView(0), R.id.nav_button).setOnClickListener(v -> {
            mPresenter.intentSelectServer();
            if (mDrawer.isDrawerOpen(GravityCompat.START))
                mDrawer.closeDrawer(GravityCompat.START);
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mRecordedFragment);
        transaction.commit();
    }

    @Override
    public void setNavTitle(String title){
        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.titleTv)).setText(title);
    }

    @Override
    public void setNavAddress(String address){
        ((TextView)mNavigationView.getHeaderView(0).findViewById(R.id.addressTv)).setText(address);
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
        startActivityForResult(new Intent(this, SelectServerActivity.class).putExtra("first",first),1);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        currentMenuId = item.getItemId();

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
            case R.id.nav_downloads:
                transaction.replace(R.id.container, mDownloadedFragment);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            if(currentMenuId == R.id.nav_recorded)
                mRecordedFragment.reload();
            if(currentMenuId == R.id.nav_downloads)
                mDownloadedFragment.reload();
        }
    }

    @Override
    public String getStringFromXml(int resId){
        return getString(resId);
    }

}