package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.view.View;

interface SelectServerPresenter extends View.OnClickListener,OnListMenuItemClickListener {
    void onDestroy();
    void getList();
    void intentAdd();
}
