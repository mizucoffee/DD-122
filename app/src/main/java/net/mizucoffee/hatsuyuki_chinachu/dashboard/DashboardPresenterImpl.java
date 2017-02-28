package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.Context;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

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
                mDashboardView.setNavTitle(sc.getName());
                mDashboardView.setNavAddress(sc.getAddress());
            }

            @Override
            public void onNotFound() {
                mDashboardView.setNavTitle(mDashboardView.getStringFromXml(R.string.server_name));
                mDashboardView.setNavAddress(mDashboardView.getStringFromXml(R.string.host_address));
                //登録しましょう通知。カードにしたいかも
            }
        });
    }
}
