package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded;

import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;

public interface RecordedPresenter {
    void onDestroy();
    void getRecorded();
    void changeListType(ListType type);
    void changeSortType(SortType type);
    void searchWord(String word);
}
