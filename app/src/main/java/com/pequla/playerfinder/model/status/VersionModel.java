package com.pequla.playerfinder.model.status;

public class VersionModel {
    private String name;
    private int protocol;

    public VersionModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }
}
