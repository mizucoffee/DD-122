package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;

public interface DownloadedView {
    void setRecyclerView(DownloadedCardRecyclerAdapter adapter, ListType listType);
    DashboardActivity getDashboardActivity();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void showSnackBar(String text);
}
