package com.programmersdiary.rushhour.astar.heuristics.blockChainHeuristics;

import com.programmersdiary.rushhour.cars.Car;
import com.programmersdiary.rushhour.cars.CarPart;
import com.programmersdiary.rushhour.trafficMap.MoveDirection;

public class Node {

    private final CarPart carPart;
    private final Edge parent;
    private Edge forwardBlocker;
    private Edge backwardBlocker;

    public Node(CarPart carPart, Edge parent) {
        this.carPart = carPart;
        this.parent = parent;
    }

    public boolean optionAlreadyChecked(Car car, MoveDirection moveDirection, int destination) {
        if(carPart == null) {
            return false;
        }
        if(car.getIdentifier() == carPart.getOwnerCar().getIdentifier()) {
            if((moveDirection == MoveDirection.FORWARD && forwardBlocker != null &&
                    destination >= forwardBlocker.getDestination()) ||
                    (moveDirection == MoveDirection.BACKWARDS && backwardBlocker != null &&
                            destination <= backwardBlocker.getDestination())) {
                return true;
            }
        }
        return (forwardBlocker != null && forwardBlocker.getChild().optionAlreadyChecked(car, moveDirection, destination)) ||
                (backwardBlocker != null && backwardBlocker.getChild().optionAlreadyChecked(car, moveDirection, destination));
    }

    public Node getParentNode() {
        return parent.getParent();
    }

    public CarPart getCarPart() {
        return carPart;
    }

    public Edge getParent() {
        return parent;
    }

    public Edge getForwardBlocker() {
        return forwardBlocker;
    }

    public Edge getBackwardBlocker() {
        return backwardBlocker;
    }

    public void setForwardBlocker(Edge forwardBlocker) {
        this.forwardBlocker = forwardBlocker;
    }

    public void setBackwardBlocker(Edge backwardBlocker) {
        this.backwardBlocker = backwardBlocker;
    }
}
