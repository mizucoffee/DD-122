package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_downloaded;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface DownloadedInteractor {
    List<ProgramItem> getDownloadedList();
}
