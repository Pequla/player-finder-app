package com.pequla.playerfinder.model.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ServerVersion {
    private String name;
    private Integer protocol;
}
