package net.mizucoffee.hatsuyuki_chinachu.dashboard;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

/**
 * Created by mizucoffee on 12/19/16.
 */

public interface DashboardView {
    void intentSelectServer(boolean first);
    SharedPreferences getActivitySharedPreferences(String name , int mode);
    void setServerConnection(ServerConnection sc);
    ServerConnection getServerConnection();
    void activityFinish();
    void setFirst(boolean b);
}
