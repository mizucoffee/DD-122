package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

public interface DashboardInteractor {
    interface OnLoadFinishedListener {
        void onSuccess(ServerConnection sc);
        void onNotFound();
    }

    void getServerConnection(OnLoadFinishedListener listener);
}
