package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.SelectServerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,DashboardView {

    private GuideFragment        mGuideFragment    = new GuideFragment();
    private LiveFragment         mLiveFragment     = new LiveFragment();
    private RecordedFragment     mRecordedFragment = new RecordedFragment();
    private TimerFragment        mTimerFragment    = new TimerFragment();

    private DashboardPresenter   mPresenter;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mPresenter = new DashboardPresenterImpl(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        View headerLayout = mNavigationView.getHeaderView(0);
        Button headerBtn = (Button)headerLayout.findViewById(R.id.nav_button);
        headerBtn.setOnClickListener(v -> mPresenter.openSelectServer());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mRecordedFragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            mDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void intentSelectServer() {
        startActivity(new Intent(this, SelectServerActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
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
}
