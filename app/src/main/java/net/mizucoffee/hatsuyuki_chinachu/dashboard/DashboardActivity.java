package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded.DownloadedFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live.LiveFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded.RecordedFragment;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivityDashboardBinding;
import net.mizucoffee.hatsuyuki_chinachu.databinding.NavHeaderDashboardBinding;
import net.mizucoffee.hatsuyuki_chinachu.settings.SettingsActivity;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static butterknife.ButterKnife.findById;

public class DashboardActivity extends AppCompatActivity {

    private GuideFragment mGuideFragment = new GuideFragment();
    private LiveFragment mLiveFragment = new LiveFragment();
    private RecordedFragment mRecordedFragment = new RecordedFragment();
    private TimerFragment mTimerFragment = new TimerFragment();
    private DownloadedFragment mDownloadedFragment = new DownloadedFragment();

    private ActivityDashboardBinding mBinding;
    private DashboardViewModel mDashboardVM;

    int currentMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDashboardVM = new DashboardViewModel(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        mBinding.setDashboardVM(mDashboardVM);
        NavHeaderDashboardBinding n = DataBindingUtil.bind(mBinding.navView.getHeaderView(0));
        n.setModel(mDashboardVM);

        subscribe();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, findById(this, R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        currentMenuId = R.id.nav_recorded;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mRecordedFragment);
        transaction.commit();

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "first");
        if (!sequence.hasFired()) mDashboardVM.isDrawerOpen.set(true);
        sequence.setConfig(config);
        sequence.addSequenceItem(mBinding.navView.getHeaderView(0), getString(R.string.lets_register), getString(R.string.ok));
        sequence.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDashboardVM.refreshConnection();
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (currentMenuId == R.id.nav_recorded) {
            if (mRecordedFragment.isSearchBarVisible())
                mRecordedFragment.setSearchBarInVisible();
        } else if (currentMenuId == R.id.nav_downloads) {
            if (mRecordedFragment.isSearchBarVisible())
                mRecordedFragment.setSearchBarInVisible();
        } else
            super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (currentMenuId == R.id.nav_recorded)
                mRecordedFragment.reload();
            if (currentMenuId == R.id.nav_downloads)
                mDownloadedFragment.reload();
        }
    }

    public void subscribe() {
        mDashboardVM.intent.subscribe(s -> {
            startActivityForResult(new Intent(this, SelectServerActivity.class), 1);
        });
        mDashboardVM.navigation.subscribe(i -> {
            currentMenuId = i;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (i) {
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
//            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        });
    }
}