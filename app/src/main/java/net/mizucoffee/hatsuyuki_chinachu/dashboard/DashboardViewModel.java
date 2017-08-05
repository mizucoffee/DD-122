package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded.DownloadedFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide.GuideFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live.LiveFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded.RecordedFragment;
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static android.app.Activity.RESULT_OK;


public class DashboardViewModel {

    enum IntentType {
        SELECT_SERVER,SETTINGS
    }

    private static GuideFragment mGuideFragment = new GuideFragment();
    private static LiveFragment mLiveFragment = new LiveFragment();
    private static RecordedFragment mRecordedFragment = new RecordedFragment();
    private static TimerFragment mTimerFragment = new TimerFragment();
    private static DownloadedFragment mDownloadedFragment = new DownloadedFragment();

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String>  mNavHost = new ObservableField<>();
    public final ObservableField<Integer> mNavBtnId = new ObservableField<>();
    public final ObservableField<Boolean> isDrawerOpen = new ObservableField<>();

    private final PublishSubject<IntentType> intentSubject = PublishSubject.create();
    final Observable<IntentType> intent = (Observable<IntentType>) intentSubject;

    private final String SERVER_NAME;
    private final String SERVER_HOST;

    private static FragmentManager mFragmentManager;

    private int currentMenuId = R.id.nav_recorded;

    //=================================
    // Constructor
    //=================================

    DashboardViewModel(DashboardActivity activity) {
        subscribe();
        SERVER_NAME = activity.getString(R.string.server_name);
        SERVER_HOST = activity.getString(R.string.host_address);
        init();
        refreshConnection();
    }

    //=================================
    // Static Method
    //=================================

    static void setFragmentManager(FragmentManager f){
        mFragmentManager = f;
    }

    //=================================
    // Private Method
    //=================================

    private void init() {
        isDrawerOpen.set(false);
        name.set(SERVER_NAME);
        mNavHost.set(SERVER_HOST);
        mNavBtnId.set(R.id.nav_recorded);
    }

    private void subscribe(){
        DataModel.Companion.getInstance().getCurrentServerConnection.subscribe(serverConnection -> {
            Shirayuki.log("success");
            name.set(serverConnection.getName());
            mNavHost.set(serverConnection.getHost());
        },e -> {});
    }
    //=================================
    // Public Method
    //=================================

    public void onClickSelectServer(View v){
        intentSubject.onNext(IntentType.SELECT_SERVER);
        isDrawerOpen.set(false);
        isDrawerOpen.notifyChange();
    }

    public boolean onNavSelected(@NonNull MenuItem item) {
        isDrawerOpen.set(false);
        isDrawerOpen.notifyChange();

        switch (item.getItemId()) {
            case R.id.nav_live:
            case R.id.nav_guide:
            case R.id.nav_recorded:
            case R.id.nav_timers:
            case R.id.nav_downloads:
                mNavBtnId.set(item.getItemId());
                currentMenuId = item.getItemId();
                break;
            case R.id.nav_settings:
                intentSubject.onNext(IntentType.SETTINGS);
                return true;
            default:
                return true;
        }
        return true;
    }

    //=================================
    // Non-access Modifiers
    //=================================

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
//            if (currentMenuId == R.id.nav_recorded)
//                mRecordedFragment.reload();
//            if (currentMenuId == R.id.nav_downloads)
//                mDownloadedFragment.reload();
        }
    }

    boolean onBackPressed() {
        if (currentMenuId == R.id.nav_recorded)
            mRecordedFragment.onBackButton();
        else if (currentMenuId == R.id.nav_live)
            mLiveFragment.onBackButton();
        else if (currentMenuId == R.id.nav_downloads) {
            mDownloadedFragment.onBackButton();
        } else
            return false;
        return true;
    }

    void refreshConnection(){
        DataModel.Companion.getInstance().getCurrentServerConnection();
    }

    //=================================
    // DataBinding Custom Listener
    //=================================

    @BindingAdapter("fragment")
    public static void setFragment(LinearLayout ll, int id){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Fragment f = null;
        switch (id) {
            case R.id.nav_live:
                f = mLiveFragment;
                break;
            case R.id.nav_guide:
                f = mGuideFragment;
                break;
            case R.id.nav_recorded:
                f = mRecordedFragment;
                break;
            case R.id.nav_timers:
                f = mTimerFragment;
                break;
            case R.id.nav_downloads:
                f = mDownloadedFragment;
                break;
        }

        if (f == null) return;
        transaction.replace(ll.getId(), f);
        transaction.commit();
    }

    @BindingAdapter("openDrawer")
    public static void setOpenDrawer(DrawerLayout drawerLayout, Boolean b){
        if(b) {
            drawerLayout.openDrawer(Gravity.START);
        } else {
            drawerLayout.closeDrawers();
        }
    }

}
