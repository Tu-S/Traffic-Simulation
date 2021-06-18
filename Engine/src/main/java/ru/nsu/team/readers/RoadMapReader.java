package ru.nsu.team.readers;

import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class RoadMapReader implements Serializable {

    public RoadMapConfiguration getMapConfig(String fileName) {
        JsonProvider parser = new JsonProvider();
        RoadMapConfiguration roadMapConfig;
        try {
            roadMapConfig = parser.readRoadMapFromJson(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return roadMapConfig;
    }
}
