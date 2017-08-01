package net.mizucoffee.hatsuyuki_chinachu.model;

public class ServerConnectionWithIndex extends ServerConnection {

    int mIndex;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int i) {
        this.mIndex = i;
    }

    public ServerConnectionWithIndex(ServerConnection sc,int i){
        id = sc.id;
        mName = sc.mName;
        mHost = sc.mHost;
        mPort = sc.mPort;
        mUsername = sc.mUsername;
        mPassword = sc.mPassword;
        mIndex = i;
    }
}