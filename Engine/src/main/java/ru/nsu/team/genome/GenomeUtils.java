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
    public static RoadMap crossbreedMaps(RoadMap parent1, RoadMap parent2) {
        int len = parent1.getRoads().size();
        assert parent1.getRoads().size() == parent2.getRoads().size();
        List<Road> roads1 = parent1.getRoads();
        List<Road> roads2 = parent2.getRoads();
        for (int i = 0; i < len; i++) {
            Road r1 = roads1.get(i);
            Road r2 = roads2.get(i);
            assert r1.getTrafficParticipants().size() == 0 && r2.getTrafficParticipants().size() == 0;
            if (r1.getFrom() != null) {
                crossbreedRoads(r1, r2);
            }
        }
        //setDefaultState(childMap, std);
        return parent1;

    }

    private static void clearPlaceOfInterests(RoadMap map) {
        for (var pl : map.getPlacesOfInterest()) {
            pl.getCars().clear();
        }
    }

    public static void clearMapFromCars(RoadMap map) {
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
     */
    private static void crossbreedRoads(Road r1, Road r2) {
        //Получаем новые ноды начала и конца путем срещивания соответвующих нод родителй
        var n1 = crossbreedNodes(r1.getFrom(), r2.getFrom());
        var n2 = crossbreedNodes(r1.getTo(), r2.getTo());
        r1.setFrom(n1);
        r1.setTo(n2);

        //new Road(r1.getId(), childFrom, childTo);
        int numberOfLanes = r1.getLanesNumber();
        assert r1.getLanesNumber() == r2.getLanesNumber();
        //скрещиваем полосы дорог родителей
        for (int i = 0; i < numberOfLanes; i++) {
            Lane l1 = r1.getLanes().get(i);
            Lane l2 = r2.getLanes().get(i);
            crossbreedLanes(l1, l2);
        }
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
//        n1.getPendingCars().clear();
//        n2.getPendingCars().clear();
        //child.setPendingCars(new HashSet<TrafficParticipant>());
        assert n1.getPendingCars().size() == 0;
        int a = (int) (20 + Math.random() * 41);
        //выбираем геном одного из родителей
        if (a % 2 == 0) {
            //n1.setGenome(CopierUtils.copy(n1.getGenome()));
            return n1;
        }
        //updTr(n1, n2);
        return n1;
    }

    private static void updTr(Node child, Node parent) {
        var chG = child.getGenome();
        var pG = parent.getGenome();
        for (int i = 0; i < pG.getTrafficLights().size(); i++) {
            var pTr = pG.getTrafficLights().get(i);
            var chTr = chG.getTrafficLights().get(i);
            chTr.setPeriod(pTr.getPeriod());
            for (int j = 0; j < pTr.getConfigs().size(); j++) {
                var pConf = pTr.getConfigs().get(j);
                var chConfig = pTr.getConfigs().get(j);
                chConfig.setDelay(pConf.getDelay());
            }
        }


    }

    /**
     * Скрещиваем полосы
     *
     * @param l1 - родитель_1
     * @param l2 - родитель_2
     */
    private static void crossbreedLanes(Lane l1, Lane l2) {
        int a = (int) (20 + Math.random() * 41);
        if (a % 2 == 0) {
            l1.setMaxSpeed(l2.getMaxSpeed());
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
