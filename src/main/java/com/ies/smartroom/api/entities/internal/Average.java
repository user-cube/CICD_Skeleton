package com.ies.smartroom.api.entities.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Average {
    private long home;
    private String room;
    private String period;
    private Double average;
}
