package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

/**
 * Created by mizucoffee on 12/19/16.
 */

public interface AddServerView {
    SharedPreferences getActivitySharedPreferences(String name,int mode);
    void finishActivity();
}
