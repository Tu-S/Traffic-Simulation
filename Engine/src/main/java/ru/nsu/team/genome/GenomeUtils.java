package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.roadmodelcreator.CopierUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GenomeUtils {

    private static int MAX_SPEED = 140;
    private static int MIN_SPEED = 20;
    private static int MAX_DELAY = 120;
    private static int MIN_DELAY = 10;
    private static byte SURVIVOR_RATE = 50; // [0-100]%
    private static RoadMap std;

    public static List<RoadMap> selection(List<RoadMap> roadMaps) {
        if (SURVIVOR_RATE < 0 || SURVIVOR_RATE > 100) {
            return roadMaps;
        }
        int roadMapsSize = roadMaps.size();
        int survivorsCount = (roadMapsSize * SURVIVOR_RATE) / 100;
        roadMaps.sort(Comparator.comparing(RoadMap::getScore));
        return roadMaps.subList(roadMapsSize - survivorsCount, roadMapsSize);
    }

    public static void setSurvivorRate(byte survivorRate) {
        SURVIVOR_RATE = survivorRate;
    }

    public static void mutateMap(RoadMap map) {
        for (Road road : map.getRoads()) {
            if (road.getFrom() != null) {
                mutateRoad(road);
            }
        }
    }


    public static void setDefaultState(RoadMap child, RoadMap standard) {

        /**
         * возвращаем время на дефолтные значения
         */
        child.setStart(standard.getStart());
        child.setCurrentTime(standard.getCurrentTime());
        child.setEndTime(standard.getEndTime());
        //child.setScore(standard.getScore());
        //child.setCurrentTime(standard.getCurrentTime());
        /**
         * Устанавливаем дефолтные значения светофоров и машинок
         */
        setDefaultRoadStats(child, standard);
        setDefaultPlaceStats(child, standard);
        //setDefaultCourseStats(child, standard);
        //setDefaultSpawnerStats(child, standard);
    }

    private static void setDefaultSpawnerStats(RoadMap child, RoadMap standard) {
        int spNum = child.getSpawnersNumber();
        assert spNum == standard.getSpawnersNumber();
        child.setSpawners(CopierUtils.copy(standard.getSpawners()));
    }

    private static void setDefaultCourseStats(RoadMap child, RoadMap standard) {
        int csNum = child.getCourseSet().size();
        assert csNum == standard.getCourseSet().size();
        child.setCourseSet(CopierUtils.copy(standard.getCourseSet()));
    }

    private static void setDefaultPlaceStats(RoadMap child, RoadMap standard) {
        int plNum = child.getPlacesOfInterestNumber();
        assert plNum == standard.getPlacesOfInterestNumber();
        child.setPlacesOfInterest(CopierUtils.copy(standard.getPlacesOfInterest()));
    }

    private static void setDefaultRoadStats(RoadMap child, RoadMap standard) {
        int rNum = child.getRoadsNumber();
        int stdRNum = standard.getRoadsNumber();
        assert rNum == stdRNum;
        for (int i = 0; i < rNum; i++) {
            Road chR = child.getRoadN(i);
            Road stdR = standard.getRoadN(i);
            setDefaultTrafficLights(chR.getFrom(), stdR.getFrom());
            setDefaultTrafficLights(chR.getTo(), stdR.getTo());
            int lNum = chR.getLanesNumber();
            int stdLNum = stdR.getLanesNumber();
            assert lNum == stdLNum;
            chR.getTrafficParticipants().clear();
            for (Lane lane : chR.getLanes()) {
                lane.getParticipants().clear();
            }
            for (int j = 0; j < lNum; j++) {
                Lane stdL = stdR.getLaneN(j);
                var tr = stdL.getParticipants();
                List<TrafficParticipant> ls = CopierUtils.copy(tr);
                assert ls != null;
                for (var t : ls) {
                    chR.addTrafficParticipant(t);
                }
            }

        }
    }

    //TODO какие значения должны быть дефолтные?
    private static void setDefaultTrafficLights(Node child, Node standard) {


    }


    /**
     * Изменяем случайным образом параметры дороги
     *
     * @param roadGenome - дорога, которую меняем
     */
    private static void mutateRoad(Road roadGenome) {
        mutateNode(roadGenome.getFrom());
        mutateNode(roadGenome.getTo());
        for (var l : roadGenome.getLanes()) {
            mutateLane(l);
        }
    }

    /**
     * Скрещиваем 2 карты
     *
     * @param parent1 - карта родитель 1
     * @param parent2 - карта родитель 2
     * @return результат скрещивания родителя_1 и родителя_2
     */
    public static RoadMap crossbreedMaps(RoadMap parent1, RoadMap parent2, RoadMap std) {
        int len = parent1.getRoads().size();
        clearMapFromCars(parent1);
        clearMapFromCars(parent2);

        assert parent1.getRoads().size() == parent2.getRoads().size();
        RoadMap childMap = CopierUtils.copy(parent1);
        assert childMap != null;

        childMap.setRoads(new ArrayList<>());
        List<Road> roads1 = parent1.getRoads();
        List<Road> roads2 = parent2.getRoads();
        for (int i = 0; i < len; i++) {
            Road r1 = roads1.get(i);
            Road r2 = roads2.get(i);
            //r1.clearCars();
            //r2.clearCars();
            if (r1.getFrom() != null) {
                Road childRoad = crossbreedRoads(r1, r2);
                childMap.addRoad(childRoad);
            } else {
                childMap.addRoad(CopierUtils.copy(r1));
            }
        }
        setDefaultState(childMap, std);
        return childMap;

    }

    private static void clearPlaceOfInterests(RoadMap map) {
        for (var pl : map.getPlacesOfInterest()) {
            pl.getCars().clear();
        }
    }

    private static void clearMapFromCars(RoadMap map) {
        clearPlaceOfInterests(map);
        for (var r : map.getRoads()) {
            r.clearCars();
        }
    }

    /**
     * Скрещиваем ноды дорог родителей
     *
     * @param r1 - родитель 1
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
        rChild.getTrafficParticipants().clear();
        assert rChild.getTrafficParticipants().size() == 0;
        return rChild;
    }

    /**
     * Срещиваем ноды
     *
     * @param n1 родитель 1
     * @param n2 родитель 2
     * @return результат скрещивания
     */
    private static Node crossbreedNodes(Node n1, Node n2) {
        assert n1.getId() == n2.getId();
        n1.getPendingCars().clear();
        n2.getPendingCars().clear();
        Node child = CopierUtils.copy(n1);
        child.setPendingCars(new HashSet<TrafficParticipant>());
        int a = (int) (20 + Math.random() * 41);
        //выбираем геном одного из родителей
        if (a % 2 == 0) {
            child.setGenome(CopierUtils.copy(n1.getGenome()));
            return child;
        }
        child.setGenome(CopierUtils.copy(n2.getGenome()));
        assert child.getPendingCars().size() == 0;
        return child;
    }

    /**
     * Скрещиваем полосы
     *
     * @param l1 - родитель_1
     * @param l2 - родитель_2
     * @return результат скрещивания
     */
    private static Lane crossbreedLanes(Lane l1, Lane l2) {
        int a = (int) (20 + Math.random() * 41);
        Lane child;
        //выбираем геном одного из родителей
        if (a % 2 == 0) {
            child = CopierUtils.copy(l1);
            //убираем из полосы машинки
            assert child != null;
            child.getParticipants().clear();
            return child;
        }
        child = CopierUtils.copy(l2);
        //убираем из полосы машинки
        assert child != null;
        child.getParticipants().clear();
        assert child.getParticipants().size() == 0;
        return child;
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
        int newPeriod = 0;
        for (var c : trafficLight.getConfigs()) {
            var newVal = (int) (MIN_DELAY + Math.random() * (MAX_DELAY - MIN_DELAY));
            newPeriod += newVal;
            c.setDelay(newVal);
        }
        trafficLight.setPeriod(newPeriod);
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
