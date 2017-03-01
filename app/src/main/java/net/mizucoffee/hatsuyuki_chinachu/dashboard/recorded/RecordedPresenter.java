package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;

public interface RecordedPresenter {
    void onDestroy();
    void getRecorded();
    void changeSort(ListType type);
}
