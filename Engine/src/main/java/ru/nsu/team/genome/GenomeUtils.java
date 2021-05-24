package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.roadmodelcreator.Copier;

import java.util.ArrayList;
import java.util.List;

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
     * Скрещиваем 2 карты
     * @param parent1 - карта родитель 1
     * @param parent2 - карта родитель 2
     * @return результат скрещивания родителя_1 и родителя_2
     */
    public static RoadMap crossbreedMaps(RoadMap parent1, RoadMap parent2) {
        int len = parent1.getRoads().size();
        assert parent1.getRoads().size() == parent2.getRoads().size();
        RoadMap childMap = Copier.copy(parent1);
        childMap.setRoads(new ArrayList<>());
        List<Road> roads1 = parent1.getRoads();
        List<Road> roads2 = parent2.getRoads();
        for (int i = 0; i < len; i++) {
            Road r1 = roads1.get(i);
            Road r2 = roads2.get(i);
            Road childRoad = crossbreedRoads(r1, r2);
            childMap.addRoad(childRoad);
        }
        return childMap;

    }

    /**
     * Скрещиваем ноды дорог родителей
     * @param r1 - родитей 1
     * @param r2 - родитель 2
     * @return результат скрещивания
     */
    private static Road crossbreedRoads(Road r1, Road r2) {
        //Получаем новые ноды начала и конца путем срещивания соответвующих нод родителй
        Node childFrom = crossbreedNodes(r1.getFrom(), r2.getFrom());
        Node childTo = crossbreedNodes(r1.getTo(), r2.getTo());

        Road rChild = new Road(r1.getId(), childFrom, childTo);
        int numberOfLanes = r1.getLanesNumber();
        assert r1.getLanesNumber() == r2.getLanesNumber();
        //скрещиваем полосы дорог родителей
        for (int i = 0; i < numberOfLanes; i++) {
            Lane l1 = r1.getLanes().get(i);
            Lane l2 = r2.getLanes().get(i);
            Lane lChild = crossbreedLanes(l1, l2);
            rChild.addLane(lChild);
        }
        return rChild;
    }

    /**
     * Срещиваем ноды
     * @param n1 родитель 1
     * @param n2 родитель 2
     * @return результат скрещивания
     */
    private static Node crossbreedNodes(Node n1, Node n2) {
        assert n1.getId() == n2.getId();
        Node child = Copier.copy(n1);
        int a = (int) (20 + Math.random() * 41);
        //выбираем геном одного из родителей
        if (a % 2 == 0) {
            child.setGenome(Copier.copy(n1.getGenome()));
            return child;
        }
        child.setGenome(Copier.copy(n2.getGenome()));
        return child;
    }

    /**
     * Скрещиваем полосы
     * @param l1 - родитель_1
     * @param l2 - родитель_2
     * @return результат скрещивания
     */
    private static Lane crossbreedLanes(Lane l1, Lane l2) {
        int a = (int) (20 + Math.random() * 41);
        //выбираем геном одного из родителей
        if (a % 2 == 0) {
            return Copier.copy(l1);
        }
        return Copier.copy(l2);
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
