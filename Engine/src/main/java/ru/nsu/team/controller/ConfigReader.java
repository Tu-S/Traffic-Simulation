package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.FileNotFoundException;

public class ConfigReader {

    public RoadMapConfiguration getMapConfig(String fileName) {
        JsonProvider parser = new JsonProvider();
        RoadMapConfiguration roadMapConfig;
        try {
            roadMapConfig = parser.readJson(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return roadMapConfig;
    }
}
