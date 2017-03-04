package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;

import retrofit2.Callback;

public interface RecordedInteractor {
    void getRecordedList(Callback callback);
    void refreshServerConnection();
    void getServerConnection(DashboardInteractor.OnLoadFinishedListener listener);
}
