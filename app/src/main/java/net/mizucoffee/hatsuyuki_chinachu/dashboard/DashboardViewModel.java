package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class DashboardViewModel {

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
    }

    public void onClickSelectServer(View v){
        intentSubject.onNext("");
        isDrawerOpen.set(false);
    }

    public void onClickNavBtn(View v){
        isDrawerOpen.set(false);
    }

    public boolean onNavSelected(@NonNull MenuItem item) {
//        mNavBtnId.set(item.getItemId());
        navigationSubject.onNext(item.getItemId());
        isDrawerOpen.set(false);
        return true;
    }

    @BindingAdapter("openDrawer")
    public static void setOpenDrawer(DrawerLayout drawerLayout, Boolean b){
        if(b) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.openDrawer(GravityCompat.START);
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

//    @BindingAdapter("fragment")
//    public static void setFragment(LinearLayout ll, int id){
//        currentMenuId = id;
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        switch (id) {
//            case R.id.nav_live:
//                transaction.replace(R.id.container, mLiveFragment);
//                break;
//            case R.id.nav_guide:
//                transaction.replace(R.id.container, mGuideFragment);
//                break;
//            case R.id.nav_recorded:
//                transaction.replace(R.id.container, mRecordedFragment);
//                break;
//            case R.id.nav_timers:
//                transaction.replace(R.id.container, mTimerFragment);
//                break;
//            case R.id.nav_downloads:
//                transaction.replace(R.id.container, mDownloadedFragment);
//                break;
//            case R.id.nav_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                break;
//        }
//
//        transaction.commit();
//    }

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
