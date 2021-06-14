package ru.nsu.team.entity.roadmap.configuration;

public class SignConfiguration {
    private String type;
    private Integer limit = 0;

    public String getType() {
        return type;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
