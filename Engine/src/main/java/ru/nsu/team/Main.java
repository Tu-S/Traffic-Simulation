package ru.nsu.team;

import ru.nsu.team.controller.RoadModelCreator;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.jsonparser.RoadMapConfigurationParser;

public class Main {
    public static void main(String[] args) {

        RoadModelCreator mapCreator = new RoadModelCreator();
        RoadMap map = mapCreator.createRoadMap("sample.json");

        System.out.println(map.getRoadNumber());

       /* RoadMapConfigurationParser parser = new RoadMapConfigurationParser();
        RoadMapConfiguration roadMapConfig;
        try {
            roadMapConfig = parser.ParseJson("sample.json");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        List<RoadConfiguration> roads = roadMapConfig.getRoads();
        List<NodeConfiguration> nodes = roadMapConfig.getNodes();

        int rN = roads.size();
        int nN = nodes.size();
        for (int i = 0; i < rN; i++) {
            System.out.printf("From %s%n", roads.get(i).getFrom());
            System.out.printf("To %s%n", roads.get(i).getTo());
            List<LaneConfiguration> lanes = roads.get(i).getLanes();
            int lN = lanes.size();
            for (int j = 0; j < lN; j++) {
                List<SignConfiguration> signs = lanes.get(j).getSigns();
                int sN = signs.size();
                for (int s = 0; s < sN; s++) {
                    SignConfiguration sign = signs.get(s);
                    System.out.printf("Sign type %s%n", sign.getType());
                    if(signs.get(s).getLimit() != null) {
                        System.out.printf("limit %s%n", sign.getLimit());
                    }
                }

            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        for (int i = 0; i < nN; i++) {
            List<Integer> nodeRoads = nodes.get(i).getRoads();
            int roadsN = nodeRoads.size();
            for (int j = 0; j < roadsN; j++) {
                System.out.printf("road %s%n", nodeRoads.get(j));

            }
            System.out.printf("greenDelay %s%n", nodes.get(i).getTrafficLightConfiguration().getDelayGreen());


        }*/


    }
}
