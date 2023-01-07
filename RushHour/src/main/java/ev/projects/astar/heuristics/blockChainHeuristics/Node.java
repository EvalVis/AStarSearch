package ev.projects.astar.heuristics.blockChainHeuristics;

import ev.projects.cars.Car;
import ev.projects.cars.CarPart;
import ev.projects.trafficMap.MoveDirection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Node {

    private final CarPart carPart;
    private final Edge parent;
    private Edge forwardBlocker;
    private Edge backwardBlocker;

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

}
