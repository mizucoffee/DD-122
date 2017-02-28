package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardInteractor;

import retrofit2.Callback;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedInteractor {
    void getRecordedList(Callback callback);
    void refreshServerConnection();
    void getServerConnection(DashboardInteractor.OnLoadFinishedListener listener);
}
