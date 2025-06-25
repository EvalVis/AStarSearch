package com.programmersdiary.rushhour.cars;

import com.programmersdiary.rushhour.obstacles.Obstacle;
import com.programmersdiary.rushhour.trafficMap.Cell;

public class CarPart extends Obstacle {

    private final Car ownerCar;
    private final int distanceFromFront;
    private final int distanceFromBack;
    private final int carForwardSteps;
    private final int carBackwardSteps;

    public CarPart(CarPart carPart, Car car) {
        ownerCar = car;
        distanceFromFront = carPart.getDistanceFromFront();
        distanceFromBack = carPart.getDistanceFromBack();
        carForwardSteps = carPart.getCarForwardSteps();
        carBackwardSteps = carPart.getCarBackwardSteps();
        cell = new Cell(carPart.getCell(), this);
    }

    public CarPart(Car ownerCar, int distanceFromBack) {
        this.ownerCar = ownerCar;
        this.distanceFromBack = distanceFromBack;
        distanceFromFront = this.ownerCar.getReversedSteps(this.distanceFromBack);
        carForwardSteps = this.distanceFromBack + 1;
        carBackwardSteps = this.distanceFromFront + 1;
    }

    public boolean isRearPart() {
        return ownerCar.getRearPart().equals(this);
    }

    @Override
    public String toString() {
        return ownerCar.toString();
    }

    @Override
    public boolean noObstacle() {
        return false;
    }

    public int getForwardDestination() {
        if(ownerCar.getCarDirection() == CarDirection.HORIZONTAL) {
            return ownerCar.getRearX() + carForwardSteps;
        }
        return ownerCar.getRearY() + carForwardSteps;
    }

    public int getBackwardDestination() {
        if(ownerCar.getCarDirection() == CarDirection.HORIZONTAL) {
            return ownerCar.getRearX() - carBackwardSteps;
        }
        return ownerCar.getRearY() - carBackwardSteps;
    }

    public Car getOwnerCar() {
        return ownerCar;
    }

    public int getDistanceFromFront() {
        return distanceFromFront;
    }

    public int getDistanceFromBack() {
        return distanceFromBack;
    }

    public int getCarForwardSteps() {
        return carForwardSteps;
    }

    public int getCarBackwardSteps() {
        return carBackwardSteps;
    }
}
