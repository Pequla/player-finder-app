package com.pequla.playerfinder.model.status;

public class StatusModel {
    private DescriptionModel description;
    private PlayerModel players;
    private VersionModel version;
    private String favicon;
    private int time;

    public StatusModel() {
    }

    public DescriptionModel getDescription() {
        return description;
    }

    public void setDescription(DescriptionModel description) {
        this.description = description;
    }

    public PlayerModel getPlayers() {
        return players;
    }

    public void setPlayers(PlayerModel players) {
        this.players = players;
    }

    public VersionModel getVersion() {
        return version;
    }

    public void setVersion(VersionModel version) {
        this.version = version;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
