package com.pequla.playerfinder.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DataModel {
    private Integer id;
    private String name;
    private String uuid;
    private String discordId;
    private String tag;
    private String avatar;
    private String guildId;
    private Date createdAt;
}
