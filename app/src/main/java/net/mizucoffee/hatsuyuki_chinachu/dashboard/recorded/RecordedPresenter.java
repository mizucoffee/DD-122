package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded.enumerate.SortType;

public interface RecordedPresenter {
    void onDestroy();
    void getRecorded();
    void changeListType(ListType type);
    void changeSortType(SortType type);
    void searchWord(String word);
}
