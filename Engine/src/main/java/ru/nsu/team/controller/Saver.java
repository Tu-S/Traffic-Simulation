package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.serializer.Serializer;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.roadmap.configuration.TrafficParticipantConfiguration;
import ru.nsu.team.entity.statistics.*;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.jsonparser.JsonProvider;
import ru.nsu.team.other.KeyValuePair;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Saver {

    public void saveMap(RoadMap map, RoadMapConfiguration mapConfiguration, String fileName) {
        Serializer serializer = new Serializer();
        List<TrafficParticipantConfiguration> trafficParticipantConfigurationList = new ArrayList<>();
        for (Road road : map.getRoads()) {
            for (TrafficParticipant trafficParticipant : road.getTrafficParticipants()) {
                TrafficParticipantConfiguration trConfig = serializer.toTrafficParticipantConfiguration(trafficParticipant);
                trafficParticipantConfigurationList.add(trConfig);
            }
        }
        long millis = map.getCurrentTime();
        mapConfiguration.setCurrentTime(toDate(millis));
        mapConfiguration.setTrafficParticipants(trafficParticipantConfigurationList);
        saveMapConfig(mapConfiguration, fileName);
    }

    public void saveHeatMap(List<KeyValuePair<Timeline,List<RoadCongestion>>> heatMap, String fileName){

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

    public void saveCarsState(List<CarState> carStates, String fileName){
        JsonProvider writer = new JsonProvider();
        writer.writeCarStatesToJson(carStates, fileName);
    }

    private String toDate(long milliseconds){
        Date date = new Date(milliseconds);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);

    }

    //TODO
    public void saveReporter() {


    }

    //TODO
    public void savePlayback() {


    }

    private void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeRoadMapToJson(config, fileName);

    }

}
