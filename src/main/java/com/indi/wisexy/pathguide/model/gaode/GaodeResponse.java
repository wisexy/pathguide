package com.indi.wisexy.pathguide.model.gaode;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GaodeResponse {

    private int status;

    private String info;

    private int infoCode;

    private int count;

    private GaodeRoute route;

}
