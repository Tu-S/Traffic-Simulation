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

import java.util.ArrayList;
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
        mapConfiguration.setTrafficParticipants(trafficParticipantConfigurationList);
        saveMapConfig(mapConfiguration, fileName);
    }

    private void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeJson(config, fileName);

    }

}
