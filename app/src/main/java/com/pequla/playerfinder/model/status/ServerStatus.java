package com.pequla.playerfinder.model.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ServerStatus {
    private ServerDescription description;
    private PlayerData players;
    private ServerVersion version;
    private String favicon;
    private Integer time;
}
