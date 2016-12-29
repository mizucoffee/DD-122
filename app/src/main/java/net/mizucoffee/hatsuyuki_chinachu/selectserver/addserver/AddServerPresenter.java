package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

interface AddServerPresenter {
    void onDestroy();
    void addServerConnection(ServerConnection sc);
    void editedServerconnection(ServerConnection sc, int position);
}
