package ev.projects.cars;

import ev.projects.obstacles.Obstacle;
import ev.projects.trafficMap.Cell;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
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

    public CarPart(@NonNull Car ownerCar, int distanceFromBack) {
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

}
