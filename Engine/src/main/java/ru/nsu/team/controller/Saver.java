package ru.nsu.team.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.Serializer;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.roadmap.configuration.TrafficParticipantConfiguration;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Saver {

    public void saveMap(RoadMap map, RoadMapConfiguration mapConfiguration, String fileName) {
        Serializer serializer = new Serializer();
        int active = map.getActiveRoadsNumber();
        int calculated = map.getCalculatedRoadsNumber();
        List<Integer> activeIds = new ArrayList<>();
        List<Integer> calculatedIds = new ArrayList<>();
        for (int i = 0; i < active; i++) {
            int id = serializer.roadToInt(map.getActiveRoadN(i));
            activeIds.add(id);
        }
        for (int i = 0; i < calculated; i++) {
            int id = serializer.roadToInt(map.getCalculatedRoadN(i));
            calculatedIds.add(id);
        }
        List<TrafficParticipantConfiguration> trafficParticipantConfigurationList = new ArrayList<>();
        int roadsNumber = map.getRoadsNumber();
        for (int i = 0; i < roadsNumber; i++) {
            Road road = map.getRoadN(i);
            int trNumber = road.getTrafficParticipantsNumber();
            for (int t = 0; t < trNumber; t++) {
                TrafficParticipant trafficParticipant = road.getTrafficParticipantN(t);
                TrafficParticipantConfiguration trConfig = serializer.toTrafficParticipantConfiguration(trafficParticipant);
                trafficParticipantConfigurationList.add(trConfig);
            }
        }
        mapConfiguration.setActiveRoads(activeIds);
        mapConfiguration.setCalculatedRoads(calculatedIds);
        mapConfiguration.setTrafficParticipants(trafficParticipantConfigurationList);
        saveMapConfig(mapConfiguration, fileName);
    }

    private void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeJson(config,fileName);

    }

}
