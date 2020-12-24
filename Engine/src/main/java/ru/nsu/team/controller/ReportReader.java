package ru.nsu.team.controller;

import ru.nsu.team.entity.statistics.HeatMapConfiguration;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.jsonparser.JsonProvider;
import ru.nsu.team.other.KeyValuePair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReportReader {

    public List<KeyValuePair<Timeline, List<RoadCongestion>>> getReportFromJson(String filepath) throws FileNotFoundException {
        JsonProvider reader = new JsonProvider();
        List<HeatMapConfiguration> configs = reader.readHeatMapConfigurationFromJson(filepath);
        List<KeyValuePair<Timeline, List<RoadCongestion>>> res = new ArrayList<>(configs.size());
        for (HeatMapConfiguration c : configs) {
            KeyValuePair<Timeline, List<RoadCongestion>> pair = new KeyValuePair<>(new Timeline(c.getStart(),c.getEnd()), c.getCongestionList());
            res.add(pair);
        }
        return res;
    }
}
