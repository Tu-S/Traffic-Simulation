package ru.nsu.team.savers;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.roadmap.configuration.TrafficParticipantConfiguration;
import ru.nsu.team.entity.roadmap.configuration.serializer.Serializer;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoadMapSaver implements Serializable {
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
        mapConfiguration.setCurrentTime(toDate(millis*1000));
        mapConfiguration.setTrafficParticipants(trafficParticipantConfigurationList);
        saveMapConfig(mapConfiguration, fileName);
    }
    private String toDate(long milliseconds){
        Date date = new Date(milliseconds);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);

    }
    private void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeRoadMapToJson(config, fileName);

    }

}
