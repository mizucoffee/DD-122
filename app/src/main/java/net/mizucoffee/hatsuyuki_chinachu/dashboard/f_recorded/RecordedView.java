package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedView {
    DashboardActivity getDashboardActivity();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void setRecyclerView(RecordedCardRecyclerAdapter adapter, ListType listType);
    void removeRecyclerView();
    void showSnackBar(String text);
}
