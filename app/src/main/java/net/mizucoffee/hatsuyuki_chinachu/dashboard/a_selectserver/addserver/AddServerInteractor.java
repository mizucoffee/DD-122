package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;

import java.util.ArrayList;

interface AddServerInteractor {
    ArrayList<ServerConnection> loadServerConnections();
    void addServerConnection(ServerConnection sc);
    void editedServerConnection(ServerConnection sc, int position);
}
