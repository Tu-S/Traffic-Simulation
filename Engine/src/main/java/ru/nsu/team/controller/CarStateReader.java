package ru.nsu.team.controller;

import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.FileNotFoundException;
import java.util.List;

public class CarStateReader {
    public List<CarState> carStateList;

    public List<CarState> getCarStateList(String filepath) throws FileNotFoundException {
        JsonProvider reader = new JsonProvider();
        return reader.readCarStatesFromJson(filepath);
    }


}
