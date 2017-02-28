package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.app.Activity;

/**
 * Created by mizucoffee on 2/27/17.
 */

public interface RecordedPresenter {
    void onDestroy();
    void getRecorded(Activity a);
}
