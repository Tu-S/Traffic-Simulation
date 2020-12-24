package ru.nsu.team.savers;

import ru.nsu.team.entity.statistics.HeatMapConfiguration;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.jsonparser.JsonProvider;
import ru.nsu.team.other.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

public class HeatMapSaver {
    public void saveHeatMap(List<KeyValuePair<Timeline, List<RoadCongestion>>> heatMap, String fileName){

        JsonProvider writer = new JsonProvider();
        writer.writeHeatMapToJson(serializeHeatMap(heatMap), fileName);

    }

    private List<HeatMapConfiguration> serializeHeatMap(List<KeyValuePair<Timeline,List<RoadCongestion>>> heatMap){
        List<HeatMapConfiguration> res = new ArrayList<>();
        for(KeyValuePair<Timeline,List<RoadCongestion>> pair : heatMap){
            HeatMapConfiguration heatMapConfiguration = new HeatMapConfiguration(pair.getKey().getBegin(), pair.getKey().getEnd(),pair.getValue());
            res.add(heatMapConfiguration);
        }
        return res;
    }
}
