package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Context;
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
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live.LiveFragment;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded.RecordedFragment;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class DashboardViewModel {

    private static GuideFragment mGuideFragment = new GuideFragment();
    private static LiveFragment mLiveFragment = new LiveFragment();
    private static RecordedFragment mRecordedFragment = new RecordedFragment();
    private static TimerFragment mTimerFragment = new TimerFragment();
    private static DownloadedFragment mDownloadedFragment = new DownloadedFragment();

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String>  mNavHost = new ObservableField<>();
    public final ObservableField<Integer> mNavBtnId = new ObservableField<>();
    public final ObservableField<Boolean> isDrawerOpen = new ObservableField<>();

    private final PublishSubject<String> intentSubject = PublishSubject.create();
    final Observable<String> intent = (Observable<String>) intentSubject;

    private final PublishSubject<Integer> navigationSubject = PublishSubject.create();
    final Observable<Integer> navigation = (Observable<Integer>) navigationSubject;

    final String SERVER_NAME;
    final String SERVER_HOST;

    DashboardModel mDashboardModel;

    int currentMenuId = R.id.nav_recorded;

    DashboardViewModel(DashboardActivity activity) {
        this.mDashboardModel = new DashboardModel(activity.getSharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
        subscribe();
        SERVER_NAME = activity.getString(R.string.server_name);
        SERVER_HOST = activity.getString(R.string.host_address);
        init();
        refreshConnection();
    }

    private void init() {
        isDrawerOpen.set(false);
        name.set(SERVER_NAME);
        mNavHost.set(SERVER_HOST);
        mNavBtnId.set(R.id.nav_recorded);
    }

    public void onClickSelectServer(View v){
        intentSubject.onNext("");
        isDrawerOpen.set(false);
    }

    public void onClickNavBtn(View v){
        isDrawerOpen.set(false);
    }

    public boolean onNavSelected(@NonNull MenuItem item) {
        isDrawerOpen.set(false);

        switch (item.getItemId()) {
            case R.id.nav_live:
            case R.id.nav_guide:
            case R.id.nav_recorded:
            case R.id.nav_timers:
            case R.id.nav_downloads:
                mNavBtnId.set(item.getItemId());
                currentMenuId = item.getItemId();
                break;
            default:
        }
//        navigationSubject.onNext(item.getItemId());
//        isDrawerOpen.set(false);
        return true;
    }

    @BindingAdapter("openDrawer")
    public static void setOpenDrawer(DrawerLayout drawerLayout, Boolean b){
        if(b) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawers();
        }
    }

    private static FragmentManager mFragmentManager;
    public static void setFragmentManager(FragmentManager f){
        mFragmentManager = f;
    }

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

    public void refreshConnection(){
        mDashboardModel.getServerConnection();
    }

    public void subscribe(){
        mDashboardModel.serverConnection.subscribe(serverConnection -> {
            Shirayuki.log("success");
            name.set(serverConnection.getName());
            mNavHost.set(serverConnection.getHost());
        });
        mDashboardModel.error.subscribe(s -> {
            Shirayuki.log("error");
            name.set(SERVER_NAME);
            mNavHost.set(SERVER_HOST);
        });
    }

}
