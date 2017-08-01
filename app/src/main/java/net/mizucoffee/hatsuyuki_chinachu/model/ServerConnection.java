package net.mizucoffee.hatsuyuki_chinachu.model;

public class ServerConnection {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected String mName;

    protected String mHost;
    protected String mPort;

    protected String mUsername;
    protected String mPassword;

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
