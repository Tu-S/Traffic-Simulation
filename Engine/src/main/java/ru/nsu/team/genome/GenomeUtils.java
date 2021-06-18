package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.roadmodelcreator.CopierUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenomeUtils {

    private static double MAX_SPEED = 110 / 3.6d;
    private static double MIN_SPEED = 20 / 3.6d;
    private static int MAX_DELAY = 120;
    private static int MIN_DELAY = 10;
    private static int SURVIVOR_RATE = 50; // [0-100]%
    private static final Random rnd = new Random();

    public static List<RoadMap> selection(List<RoadMap> generation, List<RoadMap> oldGeneration) {
        if (SURVIVOR_RATE < 0 || SURVIVOR_RATE >= 100) {
            return generation;
        }
        removeClones(generation, RoadMap::getScore);
        int roadMapsSize = generation.size();
        int survivorsCount = (roadMapsSize * SURVIVOR_RATE) / 100;
        generation.sort(Comparator.comparing(RoadMap::getScore));
        oldGeneration.sort(Comparator.comparing(RoadMap::getScore));
        List<RoadMap> newGeneration = new ArrayList<>();
        Collections.reverse(generation);
        int i = 0;
        for (RoadMap roadMap : generation) {
            if (i == survivorsCount) {
                i = 0;
                for (RoadMap roadMapOld : oldGeneration) {
                    if (i != survivorsCount) {
                        i++;
                        continue;
                    }
                    newGeneration.add(roadMapOld);
                }
                break;
            }
            i++;
            newGeneration.add(roadMap);
        }
        return newGeneration;
    }

    public static void setSurvivorRate(int survivorRate) {
        SURVIVOR_RATE = survivorRate;
    }

    @SafeVarargs
    private static <T> void removeClones(List<T> list, Function<T, ?>... functions) {
        Set<List<?>> set = new HashSet<>();
        ListIterator<T> iterator = list.listIterator();

        while (iterator.hasNext()) {
            T element = iterator.next();
            List<?> functionResults = Arrays.stream(functions)
                    .map(function -> function.apply(element))
                    .collect(Collectors.toList());

            if (!set.add(functionResults)) {
                iterator.remove();
            }
        }
    }

    public static void mutateMap(RoadMap map, double mutationRate) {
        for (Road road : map.getRoads()) {
            if (road.getFrom() != null) {
                mutateRoad(road, mutationRate);
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
    private static void mutateRoad(Road roadGenome, double mutationRate) {
        mutateNode(roadGenome.getFrom(), mutationRate);
        mutateNode(roadGenome.getTo(), mutationRate);
        for (var l : roadGenome.getLanes()) {
            mutateLane(l, mutationRate);
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
     * Скрещиваем ноды
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
        //выбираем геном одного из родителей
        if (rnd.nextBoolean()) {
            //n1.setGenome(CopierUtils.copy(n1.getGenome()));
            return n1;
        }
        updTr(n1, n2);
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
            chTr.setPeriod(chTr.getConfigs().stream().mapToInt(TrafficLightConfig::getDelay).sum());
        }
    }

    /**
     * Скрещиваем полосы
     *
     * @param l1 - родитель_1
     * @param l2 - родитель_2
     */
    private static void crossbreedLanes(Lane l1, Lane l2) {
        if (rnd.nextBoolean()) {
            l1.setMaxSpeed(l2.getMaxSpeed());
        }
    }


    /**
     * Изменяем случайным образом параметры ноды
     *
     * @param nodeGenome
     * @param mutationRate
     */
    private static void mutateNode(Node nodeGenome, double mutationRate) {
        for (var t : nodeGenome.getTrafficLights()) {
            mutateTrafficLight(t, mutationRate);
        }
    }

    private static void mutateTrafficLight(TrafficLight trafficLight, double mutationRate) {
        List<TrafficLightConfig> configs = trafficLight.getConfigs().stream()
                .map(conf -> mutateTrafficLightConfig(conf, mutationRate)).collect(Collectors.toList());
        trafficLight.setConfigs(configs);
        trafficLight.setPeriod(configs.stream().mapToInt(TrafficLightConfig::getDelay).sum());
    }

    private static TrafficLightConfig mutateTrafficLightConfig(TrafficLightConfig original, double mutationRate) {
        if (Math.random() < mutationRate) {
            return original;
        }
        int delay = Math.max(MIN_DELAY, Math.min(MAX_DELAY, original.getDelay() + rnd.nextInt(21) - 10));
        return new TrafficLightConfig(delay, original.getRoads());
    }

    /**
     * Изменяем случайным образом параметры полосы
     *
     * @param laneGenome - полоса, которую изменяем
     */
    private static void mutateLane(Lane laneGenome, double mutationRate) {
        if (Math.random() < mutationRate) {
            return;
        }
        var newSpeed = Math.max(MIN_SPEED, Math.min(MAX_SPEED,
                laneGenome.getMaxSpeed() + (rnd.nextInt(5) - 2) * (10 / 3.6d)));
        laneGenome.setMaxSpeed(newSpeed);
    }

}
