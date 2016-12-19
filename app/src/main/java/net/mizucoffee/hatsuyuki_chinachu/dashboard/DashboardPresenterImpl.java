package net.mizucoffee.hatsuyuki_chinachu.dashboard;

/**
 * Created by mizucoffee on 12/19/16.
 */

public class DashboardPresenterImpl implements DashboardPresenter {

    DashboardView mDashboardView;
    DashboardInteractor mDashboardInteractor;

    public DashboardPresenterImpl(DashboardView dashboardView) {
        this.mDashboardView = dashboardView;
        this.mDashboardInteractor = new DashboardInteractorImpl();
    }

    @Override
    public void onDestroy() {
        mDashboardView = null;
    }

    @Override
    public void openSelectServer() {
        mDashboardView.intentSelectServer();
    }
}
