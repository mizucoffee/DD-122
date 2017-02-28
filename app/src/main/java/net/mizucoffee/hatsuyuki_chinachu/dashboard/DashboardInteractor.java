package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

/**
 * Created by mizucoffee on 12/19/16.
 */

public interface DashboardInteractor {
    interface OnLoadFinishedListener {
        void onSuccess(ServerConnection sc);
        void onNotFound();
    }

    void getServerConnection(OnLoadFinishedListener listener);
}
