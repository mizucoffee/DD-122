package net.mizucoffee.hatsuyuki_chinachu.selectserver;

import android.view.View;

/**
 * Created by mizucoffee on 12/20/16.
 */

public interface SelectServerPresenter extends View.OnClickListener,OnListMenuItemClickListener {
    void onDestroy();
    void getList();
    void intentAdd();
}
