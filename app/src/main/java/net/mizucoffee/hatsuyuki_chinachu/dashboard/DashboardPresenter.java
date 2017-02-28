package net.mizucoffee.hatsuyuki_chinachu.dashboard;

/**
 * Created by mizucoffee on 12/19/16.
 */

public interface DashboardPresenter {
    void onDestroy();
    void intentSelectServer();
    void refreshConnection();
}
