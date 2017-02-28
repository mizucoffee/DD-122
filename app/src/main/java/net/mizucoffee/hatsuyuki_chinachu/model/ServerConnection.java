package net.mizucoffee.hatsuyuki_chinachu.model;

public class ServerConnection {

    private String mName;

    private String mHost;
    private String mPort;

    private String mUsername;
    private String mPassword;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        this.mHost = host;
    }

    public String getPort() {
        return mPort;
    }

    public void setPort(String port) {
        this.mPort = port;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getAddress(){
        return getHost() + ":" + getPort();
    }
}
