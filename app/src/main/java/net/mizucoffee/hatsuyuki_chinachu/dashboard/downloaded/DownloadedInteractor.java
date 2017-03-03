package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface DownloadedInteractor {
    List<Recorded> getDownloadedList();
    void setDownloadedList(List<Recorded> recorded);
}
