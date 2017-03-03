package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface DownloadedView {
    void setRecyclerView(DownloadedCardRecyclerAdapter adapter, ListType listType);
    DashboardActivity getDashboardActivity();
    void removeRecyclerView();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void showSnackBar(String text);
}
