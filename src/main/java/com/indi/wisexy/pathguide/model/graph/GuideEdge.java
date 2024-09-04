package com.indi.wisexy.pathguide.model.graph;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@ToString
public class GuideEdge implements Comparable<GuideEdge>{

    private int duration;

    private double distance;

    private GuideLocation from;

    private GuideLocation to;

    public int hashCode() {
        return from.hashCode() + to.hashCode() + String.valueOf(distance).hashCode() + duration;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GuideEdge)) {
            return false;
        }
        if (this.from.equals(((GuideEdge) other).from) && this.to.equals(((GuideEdge) other).to)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull GuideEdge other) {
        if (Math.abs(this.distance - other.distance) < Math.abs(Math.min(this.distance, other.distance) * 0.1)) {
            return this.duration - other.duration;
        }
        if (Math.abs(this.duration - other.duration) > Math.abs(Math.min(this.duration, other.duration) * 0.1)) {
            return this.duration - other.duration;
        }

        return this.distance - other.distance < 0 ? -1 : 1;
    }
}
