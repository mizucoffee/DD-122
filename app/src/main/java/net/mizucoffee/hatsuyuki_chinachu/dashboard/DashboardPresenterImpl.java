package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

/**
 * Created by mizucoffee on 12/19/16.
 */

public class DashboardPresenterImpl implements DashboardPresenter {

    DashboardView mDashboardView;
    DashboardInteractor mDashboardInteractor;

    public DashboardPresenterImpl(DashboardView dashboardView) {
        this.mDashboardView = dashboardView;
        this.mDashboardInteractor = new DashboardInteractorImpl(mDashboardView.getActivitySharedPreferences("HatsuyukiChinachu", Context.MODE_PRIVATE));
    }

    @Override
    public void onDestroy() {
        mDashboardView = null;
    }

    @Override
    public void intentSelectServer() {
        mDashboardView.intentSelectServer(false);
    }

    @Override
    public void refreshConnection(){
        mDashboardInteractor.getServerConnection(new DashboardInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(ServerConnection sc) {
                Shirayuki.log(sc.getName());
                mDashboardView.setServerConnection(sc);
                mDashboardView.setNavTitle(sc.getName());
                mDashboardView.setNavAddress(sc.getAddress());
            }

            @Override
            public void onNotFound() {
                mDashboardView.intentSelectServer(true);
                mDashboardView.activityFinish();
                mDashboardView.setFirst(true);
            }
        });
    }
}
