package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.serializer.Serializer;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.roadmap.configuration.TrafficParticipantConfiguration;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.jsonparser.JsonProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
        /*mapConfiguration.setCurrentTime(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));*/
        mapConfiguration.setTrafficParticipants(trafficParticipantConfigurationList);
        saveMapConfig(mapConfiguration, fileName);
    }

    private String toDate(long milliseconds){
        Date date = new Date(milliseconds);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = dateFormat.format(date);
        return  dateFormatted;

    }

    //TODO
    public void saveReporter() {


    }

    //TODO
    public void savePlayback() {


    }

    private void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeJson(config, fileName);

    }

}
