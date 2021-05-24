package ru.nsu.team.roadmodelcreator;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CopierUtils {

    public static List<RoadMap> makeMaps(RoadMapConfiguration roadMapConfig, int count) {
        List<RoadMap> maps = new ArrayList<>(count);
        var creator = new RoadModelCreator();
        for (int i = 0; i < count; i++) {
            maps.add(creator.createRoadMap(roadMapConfig));
        }
        return maps;
    }

    public static <T> T copy(Object target) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(target);
            objectOut.flush();
            objectOut.close();
            out.close();
            byte[] byteData = out.toByteArray();
            ByteArrayInputStream bIn = new ByteArrayInputStream(byteData);
            return (T) new ObjectInputStream(bIn).readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return null;
    }
}
