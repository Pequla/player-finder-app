package com.pequla.playerfinder.model.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StatusModel {
    private DescriptionModel description;
    private PlayerModel players;
    private VersionModel version;
    private String favicon;
    private int time;
}
