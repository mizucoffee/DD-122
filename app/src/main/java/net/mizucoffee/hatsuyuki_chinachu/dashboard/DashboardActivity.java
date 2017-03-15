package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.SelectServerActivity;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivityDashboardBinding;
import net.mizucoffee.hatsuyuki_chinachu.databinding.NavHeaderDashboardBinding;
import net.mizucoffee.hatsuyuki_chinachu.settings.SettingsActivity;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static butterknife.ButterKnife.findById;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding mBinding;
    private DashboardViewModel mDashboardVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDashboardVM = new DashboardViewModel(this);
        DashboardViewModel.setFragmentManager(getSupportFragmentManager());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        mBinding.setDashboardVM(mDashboardVM);
        setSupportActionBar(mBinding.toolbar);

        NavHeaderDashboardBinding n = NavHeaderDashboardBinding.bind(mBinding.navView.getHeaderView(0));
        n.setModel(mDashboardVM);

        subscribe();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
            mDashboardVM.isDrawerOpen.set(false);
        else if (!mDashboardVM.onBackPressed())
            super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDashboardVM.onActivityResult(requestCode, resultCode, data);
    }

    public void subscribe() {
        mDashboardVM.intent.subscribe(intentType -> {
            if(intentType == DashboardViewModel.IntentType.SELECT_SERVER)
                startActivityForResult(new Intent(this, SelectServerActivity.class), 1);
            else if(intentType == DashboardViewModel.IntentType.SETTINGS)
                startActivity(new Intent(this, SettingsActivity.class));
        });
    }
}