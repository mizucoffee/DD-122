package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;

public interface LiveView {
    DashboardActivity getDashboardActivity();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void setRecyclerView(LiveCardRecyclerAdapter adapter, ListType listType);
    void removeRecyclerView();
    void showSnackBar(String text);
}
