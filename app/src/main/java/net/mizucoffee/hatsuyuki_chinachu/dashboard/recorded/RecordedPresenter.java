package net.mizucoffee.hatsuyuki_chinachu.dashboard.recorded;

import android.app.Activity;

public interface RecordedPresenter {
    void onDestroy();
    void getRecorded(Activity a);
}
