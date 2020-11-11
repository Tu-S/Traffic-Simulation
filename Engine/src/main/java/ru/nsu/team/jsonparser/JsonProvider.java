package ru.nsu.team.jsonparser;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class JsonProvider {

    public RoadMapConfiguration ParseJson(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        return gson.fromJson(reader, RoadMapConfiguration.class);
    }
}
