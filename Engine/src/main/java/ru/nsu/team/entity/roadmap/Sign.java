package ru.nsu.team.entity.roadmap;

import java.io.Serializable;

public class Sign implements Serializable {
    private String type;
    private int limit;


    public Sign(String type, int limit) {
        this.type = type;
        this.limit = limit;

    }

    public String getType() {
        return type;
    }

    public int getLimit() {
        return limit;
    }
}
