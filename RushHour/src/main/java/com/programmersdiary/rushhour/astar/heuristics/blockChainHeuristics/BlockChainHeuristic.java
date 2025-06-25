package com.programmersdiary.rushhour.astar.heuristics.blockChainHeuristics;

import com.programmersdiary.rushhour.cars.Car;
import com.programmersdiary.rushhour.cars.CarPart;
import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.rushhour.obstacles.ClearObstacle;
import com.programmersdiary.rushhour.obstacles.Obstacle;
import com.programmersdiary.rushhour.trafficMap.MoveDirection;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;

import java.util.*;

public class BlockChainHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public BlockChainHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        return new BlockChainHeuristicCalculator(aStarObject.getCurrentStateData(), maxMoveCount).calculateHeuristic();
    }

    private static class BlockChainHeuristicCalculator {

        private final List<Node> clearNodes = new ArrayList<>();
        private final HashMap<Car, Integer> forwardMax = new HashMap<>();
        private final HashMap<Car, Integer> backwardMax = new HashMap<>();

        private final TrafficMap trafficMap;
        private final int maxMoveCount;

        public BlockChainHeuristicCalculator(TrafficMap trafficMap, int maxMoveCount) {
            this.trafficMap = trafficMap;
            this.maxMoveCount = maxMoveCount;
        }

        private int calculateHeuristic() {
            initializeSequence();
            return getMoveCount();
        }

        private void initializeSequence() {
            Car mainCar = trafficMap.getMainCar();
            int destination = trafficMap.getFinishX();
            Node sequence = new Node(mainCar.getRearPart(), null);
            Obstacle obstacle = trafficMap.getObstacle(mainCar, destination);
            MoveDirection moveDirection = trafficMap.getMoveDirection(mainCar, destination);
            if(obstacle instanceof CarPart carPart) {
                Edge edge = new Edge(destination, sequence, carPart);
                if(moveDirection == MoveDirection.FORWARD) {
                    sequence.setForwardBlocker(edge);
                    forwardMax.put(carPart.getOwnerCar(), destination);
                }
                else {
                    sequence.setBackwardBlocker(edge);
                    backwardMax.put(carPart.getOwnerCar(), destination);
                }
                getBlockerSequence(edge.getChild());
            }
            else if(obstacle instanceof ClearObstacle) {
                Edge edge = new Edge(destination, sequence, (CarPart) null);
                if(moveDirection == MoveDirection.FORWARD) {
                    sequence.setForwardBlocker(edge);
                }
                else {
                    sequence.setBackwardBlocker(edge);
                }
                clearNodes.add(edge.getChild());
            }
        }

        public int getMoveCount() {
            if(clearNodes.isEmpty()) {
                return 0;
            }
            int moveCount = 0;
            Node currentNode = clearNodes.get(0);
            while(currentNode != null && currentNode.getParent() != null && moveCount < maxMoveCount) {
                moveCount++;
                currentNode = currentNode.getParentNode();
            }
            return moveCount;
        }

        private void getBlockerSequence(Node node) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);
            while(!queue.isEmpty()) {
                Node currentNode = queue.remove();
                CarPart carPart = currentNode.getCarPart();
                int forwardDestination = carPart.getForwardDestination();
                int backwardDestination = carPart.getBackwardDestination();
                if (movementAllowed(MoveDirection.FORWARD, currentNode)) {
                    Obstacle forwardObstacle = trafficMap.getObstacle(carPart.getOwnerCar(), forwardDestination);
                    if (forwardObstacle instanceof ClearObstacle) {
                        Edge edge = new Edge(forwardDestination, currentNode, (CarPart) null);
                        currentNode.setForwardBlocker(edge);
                        clearNodes.add(edge.getChild());
                        break;
                    }
                    if (forwardObstacle instanceof CarPart blockerPart) {
                        if (canRequestWay(carPart, MoveDirection.FORWARD, forwardDestination)) {
                            Edge edge = new Edge(forwardDestination, currentNode, blockerPart);
                            forwardMax.put(carPart.getOwnerCar(), forwardDestination);
                            currentNode.setForwardBlocker(edge);
                            queue.add(edge.getChild());
                        }
                    }
                }
                if (movementAllowed(MoveDirection.BACKWARDS, currentNode)) {
                    Obstacle backwardObstacle = trafficMap.getObstacle(carPart.getOwnerCar(), backwardDestination);
                    if (backwardObstacle instanceof CarPart blockerPart) {
                        if (canRequestWay(carPart, MoveDirection.BACKWARDS, backwardDestination)) {
                            Edge edge = new Edge(backwardDestination, currentNode, blockerPart);
                            backwardMax.put(carPart.getOwnerCar(), backwardDestination);
                            currentNode.setBackwardBlocker(edge);
                            queue.add(edge.getChild());
                        }
                    }
                    if (backwardObstacle instanceof ClearObstacle) {
                        Edge edge = new Edge(backwardDestination, currentNode, (CarPart) null);
                        currentNode.setBackwardBlocker(edge);
                        clearNodes.add(edge.getChild());
                        break;
                    }
                }
            }
        }

        private boolean movementAllowed(MoveDirection moveDirection, Node node) {
            Car blocker = node.getCarPart().getOwnerCar();
            Node parentNode = node.getParentNode();
            Car requestor = parentNode.getCarPart().getOwnerCar();
            if(requestor.getCarDirection() == blocker.getCarDirection()) {
                if(parentNode.getForwardBlocker() != null && parentNode.getForwardBlocker().getChild() != null &&
                        parentNode.getForwardBlocker().getChild().getCarPart() != null &&
                        parentNode.getForwardBlocker().getChild().getCarPart().getOwnerCar().getIdentifier() == blocker.getIdentifier() &&
                        moveDirection == MoveDirection.BACKWARDS) {
                    return false;
                }
                return parentNode.getBackwardBlocker() == null || parentNode.getBackwardBlocker().getChild() == null ||
                        parentNode.getBackwardBlocker().getChild().getCarPart() == null ||
                        parentNode.getBackwardBlocker().getChild().getCarPart().getOwnerCar().getIdentifier() != blocker.getIdentifier() ||
                        moveDirection != MoveDirection.FORWARD;
            }
            return true;
        }

        private boolean canRequestWay(CarPart carPart, MoveDirection moveDirection, int destination) {
            Car car = carPart.getOwnerCar();
            if(moveDirection == MoveDirection.FORWARD) {
                return forwardMax.get(car) == null || forwardMax.get(car) > destination;
            }
            return backwardMax.get(car) == null || backwardMax.get(car) < destination;
        }

    }

}
