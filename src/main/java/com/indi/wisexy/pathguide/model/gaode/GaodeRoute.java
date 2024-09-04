package com.indi.wisexy.pathguide.model.gaode;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class GaodeRoute {

    private String origin;

    private String destination;

    private String taxi_cost;

    private List<GaodePath> paths;

}
