package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType;
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType;

public interface DownloadedPresenter {
    void onDestroy();
    void getDownloaded();
    void changeListType(ListType type);
    void changeSortType(SortType type);
    void searchWord(String word);
}
