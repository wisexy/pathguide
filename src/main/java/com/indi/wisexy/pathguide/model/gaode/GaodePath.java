package com.indi.wisexy.pathguide.model.gaode;

import lombok.Data;

@Data
public class GaodePath {

    private int distance;

    private int duration;

    private String tolls;

    private String toll_distance;

    private String restriction;

    private String traffic_lights;

}
