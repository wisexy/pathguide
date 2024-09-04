package com.indi.wisexy.pathguide.model.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuideLocation {

    private double latitude;

    private double longitude;

    public int hashCode() {
        return (String.valueOf(latitude).hashCode() + String.valueOf(longitude).hashCode()) % 100000007;
    }

    public boolean equals(GuideLocation other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return Math.abs(this.latitude - other.latitude) < 0.0003 && Math.abs(this.longitude - other.longitude) < 0.0003;
    }

}
