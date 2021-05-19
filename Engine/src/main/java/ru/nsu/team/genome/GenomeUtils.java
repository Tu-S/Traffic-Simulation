package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.TrafficLight;

public class GenomeUtils {

    private static int MAX_SPEED = 140;
    private static int MIN_SPEED = 20;
    private static int MAX_DELAY = 120;
    private static int MIN_DELAY = 10;

    /**
     * Изменяем случайным образом параметры дороги
     *
     * @param roadGenome - дорога, которую меняем
     */
    public static void mutateRoad(Road roadGenome) {
        mutateNode(roadGenome.getFrom());
        mutateNode(roadGenome.getTo());
        for (var l : roadGenome.getLanes()) {
            mutateLane(l);
        }
    }

    /**
     * Изменяем случайным образом параметры ноды
     *
     * @param nodeGenome
     */
    private static void mutateNode(Node nodeGenome) {
        for (var t : nodeGenome.getTrafficLights()) {
            mutateTrafficLight(t);
        }
    }

    private static void mutateTrafficLight(TrafficLight trafficLight) {
        for (var c : trafficLight.getConfigs()) {
            c.setDelay((int) (MIN_DELAY + Math.random() * (MAX_DELAY - MIN_DELAY)));
        }
    }

    /**
     * Изменяем случайным образом параметры полосы
     *
     * @param laneGenome - полоса, которую изменяем
     */
    private static void mutateLane(Lane laneGenome) {
        var newSpeed = MIN_SPEED + Math.random() * (MAX_SPEED - MIN_SPEED);
        laneGenome.setMaxSpeed((int) newSpeed);
    }


}
