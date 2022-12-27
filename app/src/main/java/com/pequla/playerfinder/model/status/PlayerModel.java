package com.pequla.playerfinder.model.status;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlayerModel {
    private int max;
    private int online;
    private ArrayList<PlayerDataModel> sample;
}
