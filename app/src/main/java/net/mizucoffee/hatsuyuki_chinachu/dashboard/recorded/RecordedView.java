package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.content.SharedPreferences;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedView {
    void setRecyclerView(List<Recorded> recorded,ListType listType);
    DashboardActivity getDashboardActivity();
    void removeRecyclerView();
    SharedPreferences getActivitySharedPreferences(String name, int mode);
    void showSnackbar(String text);
}
