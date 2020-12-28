package ru.nsu.team.savers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.team.entity.report.HeatmapFrame;
import ru.nsu.team.entity.statistics.HeatMapConfiguration;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.jsonparser.JsonProvider;
import ru.nsu.team.other.KeyValuePair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeatMapSaver {
    public void saveHeatMap(List<HeatmapFrame> heatMap, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(heatMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
