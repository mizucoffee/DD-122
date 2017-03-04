package net.mizucoffee.hatsuyuki_chinachu.dashboard.downloaded;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface DownloadedInteractor {
    List<Program> getDownloadedList();
}
