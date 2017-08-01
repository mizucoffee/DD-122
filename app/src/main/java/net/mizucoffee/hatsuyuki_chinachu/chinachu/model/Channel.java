package net.mizucoffee.hatsuyuki_chinachu.chinachu.model;

public class Channel {

    private String type;
    private String channel;
    private String name;
    private String id;
    private int sid;
    private int nid;
    private boolean hasLogoData;
    private Integer n;

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public boolean isHasLogoData() {
        return hasLogoData;
    }

    public void setHasLogoData(boolean hasLogoData) {
        this.hasLogoData = hasLogoData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}