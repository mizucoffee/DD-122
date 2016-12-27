package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

/**
 * Created by mizucoffee on 12/20/16.
 */

public interface AddServerPresenter {
    void onDestroy();
    void save(ServerConnection sc);
    void edited(ServerConnection sc,int position);
}
