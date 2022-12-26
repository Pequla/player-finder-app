package com.pequla.playerfinder.model.status;

import java.util.ArrayList;

public class PlayerModel {

    private int max;
    private int online;
    private ArrayList<PlayerDataModel> sample;

    public PlayerModel() {
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public ArrayList<PlayerDataModel> getSample() {
        return sample;
    }

    public void setSample(ArrayList<PlayerDataModel> sample) {
        this.sample = sample;
    }
}
