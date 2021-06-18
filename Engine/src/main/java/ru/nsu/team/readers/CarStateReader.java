package ru.nsu.team.readers;

import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.jsonparser.JsonProvider;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

public class CarStateReader implements Serializable {
    public List<CarState> carStateList;

    public List<CarState> getCarStateList(String filepath) throws FileNotFoundException {
        JsonProvider reader = new JsonProvider();
        return reader.readCarStatesFromJson(filepath);
    }


}
