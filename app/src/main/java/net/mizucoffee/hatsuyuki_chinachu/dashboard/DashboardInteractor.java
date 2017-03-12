package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

/**
 * Created by mizucoffee on 3/12/17.
 */

public interface DashboardInteractor {
    interface OnLoadFinishedListener{
        void onSuccess(ServerConnection sc);
        void onNotFound();
    }
}
