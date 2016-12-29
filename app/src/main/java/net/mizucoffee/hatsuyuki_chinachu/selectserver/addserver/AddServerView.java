package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;

interface AddServerView {
    SharedPreferences getActivitySharedPreferences(String name,int mode);
    void finishActivity();
}
