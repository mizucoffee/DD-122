package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;

import java.util.List;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedView {
    void setRecyclerView(List<Recorded> recorded);
}
