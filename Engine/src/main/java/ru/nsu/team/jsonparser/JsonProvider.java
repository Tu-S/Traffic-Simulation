package ru.nsu.team.jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.entity.statistics.HeatMapConfiguration;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.other.KeyValuePair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class JsonProvider {

    public RoadMapConfiguration readRoadMapFromJson(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        return gson.fromJson(reader, RoadMapConfiguration.class);
    }
    public List<CarState> readCarStatesFromJson(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        CarState[] states = gson.fromJson(reader, CarState[].class);
        return Arrays.asList(states);
    }

    public List<HeatMapConfiguration> readHeatMapConfigurationFromJson(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        HeatMapConfiguration[] states = gson.fromJson(reader, HeatMapConfiguration[].class);
        return Arrays.asList(states);
    }




    public void writeRoadMapToJson(RoadMapConfiguration config, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCarStatesToJson(List<CarState> carStates, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(carStates, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHeatMapToJson(List<HeatMapConfiguration> heatMar, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(heatMar, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}