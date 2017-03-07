package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_live;

import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;

public interface LivePresenter {
    void onDestroy();
    void getBroadcasting();
    void changeSortType(SortType type);
    void searchWord(String word);
}
