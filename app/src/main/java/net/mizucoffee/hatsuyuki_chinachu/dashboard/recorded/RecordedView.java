package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedView {
    void setRecyclerView(RecordedCardRecyclerAdapter adapter, ListType listType);
    DashboardActivity getDashboardActivity();
    void removeRecyclerView();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void showSnackBar(String text);
}
