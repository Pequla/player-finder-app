package com.pequla.playerfinder.model.status;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlayerData {
    private Integer max;
    private Integer online;
    private ArrayList<PlayerModel> list;
}
