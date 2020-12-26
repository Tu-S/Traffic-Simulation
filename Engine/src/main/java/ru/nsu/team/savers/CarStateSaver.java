package ru.nsu.team.savers;

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

public class CarStateSaver {


    public void saveCarsState(List<CarState> carStates, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeCarStatesToJson(carStates, fileName);
    }


}
