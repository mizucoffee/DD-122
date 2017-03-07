package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

interface SelectServerInteractor {

    interface OnLoadFinishedListener {
        void onSuccess(ArrayList<ServerConnection> sc);
        void onNotFound();
    }

    void loadServerConnections(OnLoadFinishedListener listener);
    void deleteServerConnection(int position);
    ServerConnection editServerConnection(int position);

    void setServerConnection(ServerConnection sc);
    ServerConnection getServerConnection();
}
