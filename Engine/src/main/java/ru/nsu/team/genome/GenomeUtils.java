package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.TrafficLight;

public class GenomeUtils {

    private static int MAX_SPEED_PART = 2;
    private static int MIN_SPEED_PART = 10;
    private static int MAX_DELAY_PART = 2;
    private static int MIN_DELAY_PART = 5;

    /**
     * Изменяем случайным образом параметры дороги
     * @param roadGenome - дорога, которую меняем
     */
    public static void mutateRoad(RoadGenome roadGenome){
        mutateNode(roadGenome.getFrom());
        mutateNode(roadGenome.getTo());

        for(var l : roadGenome.getLanes()){
            mutateLane(l);
        }
    }

    /**
     * Изменяем случайным образом параметры ноды
     * @param nodeGenome
     */
    private static void mutateNode(NodeGenome nodeGenome){
        for(var t : nodeGenome.getTrafficLights()){
            mutateTrafficLight(t);
        }
    }
    private static void mutateTrafficLight(TrafficLight trafficLight){
        for(var c : trafficLight.getConfigs()){
            var curDelay = c.getDelay();
            var maxDelay = curDelay + curDelay/MAX_DELAY_PART;
            var minDelay = curDelay - curDelay/MIN_DELAY_PART;
            c.setDelay((int)(minDelay + Math.random()*(maxDelay)));
        }
    }

    /**
     * Изменяем случайным образом параметры полосы
     * @param laneGenome - полоса, которую изменяем
     */
    private static void mutateLane(LaneGenome laneGenome){
         var maxSpeed = laneGenome.getMaxSpeed();
         var incr = maxSpeed + maxSpeed/MAX_SPEED_PART;
         var decr = maxSpeed - maxSpeed/MIN_SPEED_PART;
         var newSpeed = decr + Math.random()*(incr-decr);
         laneGenome.setMaxSpeed(newSpeed);
    }





}
