package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;

public interface LiveView {
    DashboardActivity getDashboardActivity();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void setRecyclerView(LiveCardRecyclerAdapter adapter);
    void removeRecyclerView();
    void showSnackBar(String text);
}
