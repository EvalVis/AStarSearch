package com.programmersdiary.rushhour.astar.aStarObjects;

import com.programmersdiary.rushhour.cars.Car;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.astar.search.MoveSequence;
import com.programmersdiary.rushhour.trafficMap.MoveDirection;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;
import java.util.HashSet;
import java.util.Set;

public class TrafficMapAStarObject extends AStarObject<TrafficMap> {

    private final TrafficMap trafficMap;

    public TrafficMapAStarObject(MoveSequence moveSequence, int gValue, TrafficMap trafficMap) {
        super(moveSequence, gValue);
        this.trafficMap = trafficMap;
    }

    @Override
    public TrafficMap getCurrentStateData() {
        return trafficMap;
    }

    @Override
    public boolean isSolved() {
        return trafficMap.mainCarExited();
    }

    @Override
    public Set<AStarObject<TrafficMap>> getNeighbours() {
        Set<AStarObject<TrafficMap>> neighbours = new HashSet<>();
        for (Car car : trafficMap.getCars()) {
            neighbours.addAll(getNeighbours(car, trafficMap, MoveDirection.FORWARD));
            neighbours.addAll(getNeighbours(car, trafficMap, MoveDirection.BACKWARDS));
        }
        return neighbours;
    }

    private Set<AStarObject<TrafficMap>> getNeighbours(Car car, TrafficMap lastTrafficMap, MoveDirection moveDirection) {
        Set<AStarObject<TrafficMap>> neighbours = new HashSet<>();
        int steps = moveDirection.getMoveSign();
        TrafficMap currentMap = new TrafficMap(lastTrafficMap);
        while(currentMap.moveCarIfPossible(car, moveDirection, Math.abs(steps)).noObstacle()) {
            neighbours.add(new TrafficMapAStarObject(new MoveSequence(
                    moveSequence, car + "" + steps), gValue + 1, currentMap));
            steps += moveDirection.getMoveSign();
            currentMap = new TrafficMap(lastTrafficMap);
        }
        return neighbours;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrafficMapAStarObject trafficMapAStarObject = (TrafficMapAStarObject) o;
        return trafficMap.toString().equals(trafficMapAStarObject.trafficMap.toString());
    }

    @Override
    public int hashCode() {
        return trafficMap.hashCode();
    }
}
