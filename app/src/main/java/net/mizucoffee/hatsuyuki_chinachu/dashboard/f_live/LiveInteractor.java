package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;

import retrofit2.Callback;

public interface LiveInteractor {
    void getLiveList(Callback callback);
    void refreshServerConnection();
    void getServerConnection(DashboardInteractor.OnLoadFinishedListener listener);
}
