package ru.nsu.team.savers;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.NodeConfiguration;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.jsonparser.JsonProvider;

public class SaverUtils {

    private static void saveMapConfig(RoadMapConfiguration config, String fileName) {
        JsonProvider writer = new JsonProvider();
        writer.writeRoadMapToJson(config, fileName);

    }

    public static void saveOptimisedMap(RoadMap map, RoadMapConfiguration config, String fileName) {
        var roadConfigs = config.getRoads();
        var nodeConfigs = config.getNodes();
        for (var road : map.getRoads()) {
            if(road.getFrom() == null){
                continue;
            }
            var roadConfig = roadConfigs.get(road.getId());
            int laneNumber = road.getLanesNumber();
            //lanes
            for (int l = 0; l < laneNumber; l++) {
                var lane = road.getLanes().get(l);
                var laneConfig = roadConfig.getLanes().get(l);
                for (var signConfig : laneConfig.getSigns()) {
                    signConfig.setLimit((int) (lane.getMaxSpeed()*3.601)); // Это пасхалочка для внимательных
                }
            }
            //lights
            var fromConfig = nodeConfigs.get(roadConfig.getFrom());
            var from = road.getFrom();
            updateTrafficLights(from, fromConfig);
            var to = road.getTo();
            var toConfig = nodeConfigs.get(roadConfig.getTo());
            updateTrafficLights(to, toConfig);
        }
        saveMapConfig(config, fileName);
    }

    private static void updateTrafficLights(Node node, NodeConfiguration configuration) {
        var trafficLights = node.getTrafficLights();
        var trafficLightConfigs = configuration.getTrafficLightConfigurations();
        var cf = configuration.getTrafficLightConfigurations();
        if(cf != null) {
            int trNum = cf.size();
            if (trNum > 0) {
                var trafficLight = trafficLights.get(0);
                var configs = trafficLight.getConfigs();
                for (int tr = 0; tr < trNum; tr++) {
                    var trConfig = trafficLightConfigs.get(tr);
                    trConfig.setDelay(configs.get(tr).getDelay());
                }
            }
        }
    }
}
