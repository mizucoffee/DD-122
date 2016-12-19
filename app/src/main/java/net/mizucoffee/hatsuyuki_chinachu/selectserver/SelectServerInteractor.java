package net.mizucoffee.hatsuyuki_chinachu.selectserver;

/**
 * Created by mizucoffee on 12/20/16.
 */

public interface SelectServerInteractor {
    interface OnGetFinishedListener {
        void onSuccess();
    }

    void getList(OnGetFinishedListener listener);
}
