package ev.projects.trafficMap;

import ev.projects.cars.Car;
import ev.projects.cars.CarDirection;
import ev.projects.cars.CarPart;
import ev.projects.obstacles.ClearObstacle;
import ev.projects.obstacles.Obstacle;
import ev.projects.obstacles.Wall;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrafficMap {

    private Cell[][] cells;
    private final int leftWallX = -1;
    private final int rightWallX;
    private final int upWallY = -1;
    private final int downWallY;
    private final List<Car> cars = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();
    private final int finishX;
    private final int finishY = 2;

    public TrafficMap(TrafficMap trafficMap) {
        rightWallX = trafficMap.getRightWallX();
        downWallY = trafficMap.getDownWallY();
        finishX = trafficMap.finishX;
        generateEmptyCells(rightWallX, downWallY);
        for(Car car : trafficMap.cars) {
            placeCarOnMap(new Car(car), car.getRearY(), car.getRearX());
        }
        for(Wall wall : trafficMap.walls) {
            placeWallOnMap(new Wall(wall), wall.getCell().getY(), wall.getCell().getX());
        }
    }

    public TrafficMap(int horizontalSize, int verticalSize, int finishX) {
        generateEmptyCells(verticalSize, horizontalSize);
        rightWallX = horizontalSize;
        downWallY = verticalSize;
        this.finishX = finishX;
    }


    public Car getMainCar() {
        return cars.stream().filter(Car::isMain).findFirst().get();
    }

    private void generateEmptyCells(int xSize, int ySize) {
        cells = new Cell[ySize][xSize];
        for(int y = 0; y < cells.length; y++) {
            Cell[] row = cells[y];
            for(int x = 0; x < row.length; x++) {
                Cell cell = new Cell(x, y);
                row[x] = cell;
            }
        }
    }

    public boolean mainCarExited() {
        CarPart occupant = (CarPart) cells[finishY][finishX].getOccupant();
        return occupant != null && occupant.isRearPart() && occupant.getOwnerCar().isMain();
    }

    public void placeWallOnMap(Wall wall, int y, int x) {
        Cell cell = cells[y][x];
        cell.setOccupant(wall);
        walls.add(wall);
    }

    public void placeWallOnMap(int y, int x) {
        placeWallOnMap(new Wall(), y, x);
    }

    public void placeCarOnMap(Car car, int topMostY, int leftMostX) {
        CarPart[] parts = car.getParts();
        CarDirection carDirection = car.getCarDirection();
        for(int i = 0; i < parts.length; i++) {
            cells[topMostY + (carDirection.getYMultiplier() * i)]
                    [leftMostX + (carDirection.getXMultiplier() * i)]
                    .setOccupant(parts[i]);
        }
        cars.add(car);
    }

    public MoveDirectionAndSteps getMoveDirectionAndSteps(Car car, int destinationX, int destinationY) {
        Cell rearCell = car.getRearPart().getCell();
        int startX = rearCell.getX();
        int startY = rearCell.getY();
        CarDirection carDirection = car.getCarDirection();
        if(carDirection == CarDirection.HORIZONTAL) {
            if(startY != destinationY) {
                throw new RuntimeException("Cannot move to this position, because car moves horizontally!");
            }
            int steps = destinationX - startX;
            return steps < 0 ? new MoveDirectionAndSteps(MoveDirection.BACKWARDS, Math.abs(steps))
                    : new MoveDirectionAndSteps(MoveDirection.FORWARD, steps);
        }
        else if(carDirection == CarDirection.VERTICAL) {
            if(startX != destinationX) {
                throw new RuntimeException("Cannot move to this position, because car moves vertically!");
            }
            int steps = destinationY - startY;
            return steps < 0 ? new MoveDirectionAndSteps(MoveDirection.BACKWARDS, Math.abs(steps))
                    : new MoveDirectionAndSteps(MoveDirection.FORWARD, steps);
        }
        else throw new RuntimeException("Unacceptable car direction!");
    }

    public Cell getDestinationCell(CarPart carPart, MoveDirection moveDirection) {
        Cell headCell = carPart.getOwnerCar().getRearPart().getCell();
        int headX = headCell.getX();
        int headY = headCell.getY();
        CarDirection carDirection = carPart.getOwnerCar().getCarDirection();
        int moveSign = moveDirection.getMoveSign();
        int destinationX = -1;
        int destinationY = -1;
        if(moveDirection == MoveDirection.FORWARD) {
            destinationX = headX + carPart.getCarForwardSteps() * moveSign * carDirection.getXMultiplier();
            destinationY = headY + carPart.getCarForwardSteps() * moveSign * carDirection.getYMultiplier();
        }
        else if(moveDirection == MoveDirection.BACKWARDS) {
            destinationX = headX + carPart.getCarBackwardSteps() * moveSign * carDirection.getXMultiplier();
            destinationY = headY + carPart.getCarBackwardSteps() * moveSign * carDirection.getYMultiplier();
        }
        if(destinationOutsidePerimeter(destinationX, destinationY)) {
            return null;
        }
        return cells[destinationY][destinationX];
    }

    public Obstacle moveCarIfPossible(Car car, Cell destinationCell) {
        MoveDirectionAndSteps moveDirectionAndSteps = getMoveDirectionAndSteps(
                car, destinationCell.getX(), destinationCell.getY());
        return moveCarIfPossible(car, moveDirectionAndSteps.getMoveDirection(), moveDirectionAndSteps.getSteps());
    }

    public Obstacle moveCarIfPossible(Car car, int destinationX, int destinationY) {
        MoveDirectionAndSteps moveDirectionAndSteps = getMoveDirectionAndSteps(car, destinationX, destinationY);
        return moveCarIfPossible(car, moveDirectionAndSteps.getMoveDirection(), moveDirectionAndSteps.getSteps());
    }

    public Obstacle moveCarIfPossible(Car car, int destination) {
        if(car.getCarDirection() == CarDirection.HORIZONTAL) {
            return moveCarIfPossible(car, destination, car.getRearY());
        }
        return moveCarIfPossible(car, car.getRearX(), destination);
    }

    public Obstacle getObstacle(Car car, int destination) {
        if(car.getCarDirection() == CarDirection.HORIZONTAL) {
            return getObstacle(car, destination, car.getRearY());
        }
        return getObstacle(car, car.getRearX(), destination);
    }

    private Obstacle getObstacle(Car car, int destinationX, int destinationY) {
        MoveDirectionAndSteps moveDirectionAndSteps = getMoveDirectionAndSteps(car, destinationX, destinationY);
        return getObstacle(car, moveDirectionAndSteps.getMoveDirection(), moveDirectionAndSteps.getSteps());
    }

    private Obstacle getObstacle(Car car, MoveDirection moveDirection, int steps) {
        if(steps == 0) {
            return new ClearObstacle();
        }
        if(steps < 0) {
            throw new RuntimeException("Steps must NOT be < 0!");
        }
        Cell startingCell;
        if(moveDirection == MoveDirection.FORWARD) {
            startingCell = car.getFrontPart().getCell();
        }
        else if(moveDirection == MoveDirection.BACKWARDS) {
            startingCell = car.getRearPart().getCell();
        }
        else throw new RuntimeException("Incorrect move direction!");
        int startingX = startingCell.getX();
        int startingY = startingCell.getY();
        CarDirection carDirection = car.getCarDirection();
        int moveSign = moveDirection.getMoveSign();
        int xMul = moveSign * carDirection.getXMultiplier();
        int yMul = moveSign * carDirection.getYMultiplier();
        return obstacleInPath(car, steps, startingX, startingY, xMul, yMul);
    }

    public Obstacle moveCarIfPossible(Car car, MoveDirection moveDirection, int steps) {
        if(steps == 0) {
            return new ClearObstacle();
        }
        if(steps < 0) {
            throw new RuntimeException("Steps must NOT be < 0!");
        }
        Cell startingCell;
        if(moveDirection == MoveDirection.FORWARD) {
            startingCell = car.getFrontPart().getCell();
        }
        else if(moveDirection == MoveDirection.BACKWARDS) {
            startingCell = car.getRearPart().getCell();
        }
        else throw new RuntimeException("Incorrect move direction!");
        int startingX = startingCell.getX();
        int startingY = startingCell.getY();
        CarDirection carDirection = car.getCarDirection();
        int moveSign = moveDirection.getMoveSign();
        int xMul = moveSign * carDirection.getXMultiplier();
        int yMul = moveSign * carDirection.getYMultiplier();
        Obstacle obstacle = obstacleInPath(car, steps, startingX, startingY, xMul, yMul);
        if(obstacle instanceof ClearObstacle) {
            moveCar(car, steps, startingX, startingY, xMul, yMul);
        }
        return obstacle;
    }

    public void moveByTriplet(String data) {
        char carIdentifier = data.charAt(0);
        MoveDirection moveDirection = data.charAt(1) == '+' ? MoveDirection.FORWARD : MoveDirection.BACKWARDS;
        int steps = Integer.parseInt(data.substring(2));
        Car car = cars.stream().filter(c -> c.getIdentifier() == carIdentifier).findFirst().get();
        moveCarIfPossible(car, moveDirection, steps);
    }

    public MoveDirection getMoveDirection(Car car, int destination) {
        if(car.getCarDirection() == CarDirection.HORIZONTAL) {
            if(car.getRearX() <= destination) {
                return MoveDirection.FORWARD;
            }
            return MoveDirection.BACKWARDS;
        }
        if(car.getRearY() <= destination) {
            return MoveDirection.FORWARD;
        }
        return MoveDirection.BACKWARDS;
    }

    public int getCarDestination(Car car, MoveDirection moveDirection, int steps) {
        int rearPosition;
        if(car.getCarDirection() == CarDirection.HORIZONTAL) {
            rearPosition = car.getRearX();
        }
        else {
            rearPosition = car.getRearY();
        }
        if(moveDirection == MoveDirection.FORWARD) {
            return rearPosition + steps;
        }
        return rearPosition - steps;
    }

    private boolean destinationOutsidePerimeter(int destinationX, int destinationY) {
        return destinationX <= leftWallX || destinationX >= rightWallX ||
                destinationY <= upWallY || destinationY >= downWallY;
    }

    private boolean wouldMoveOutsidePerimeter(int steps, int startingX, int startingY, int xMul, int yMul) {
        int farX = startingX + steps * xMul;
        int farY = startingY + steps * yMul;
        return destinationOutsidePerimeter(farX, farY);
    }

    private boolean blockingPartsMakeWall(Car car, int steps,
                                          int startingX, int startingY, int xMul, int yMul) {
        int sameAxisCars = 0;
        int xToCheck = startingX + xMul;
        int yToCheck = startingY + yMul;
        int i = 1;
        while(xToCheck > -1 && xToCheck < rightWallX && yToCheck > -1 && yToCheck < downWallY) {
            Obstacle occupant = cells[yToCheck][xToCheck].getOccupant();
            if(occupant instanceof CarPart carPart
                    && carPart.getOwnerCar() != car
                    && carPart.getOwnerCar().getCarDirection() == car.getCarDirection()) {
                sameAxisCars++;
            }
            i++;
            xToCheck = startingX + i * xMul;
            yToCheck = startingY + i * yMul;
        }
        int possibleDistanceToMove = i - sameAxisCars;
        return steps > possibleDistanceToMove;
    }

    private Obstacle blockingObstacle(Car car, int steps, int startingX, int startingY, int xMul, int yMul) {
        for(int i = 1; i <= steps; i++) {
            int XToCheck = startingX + i * xMul;
            int YToCheck = startingY + i * yMul;
            Obstacle occupant = cells[YToCheck][XToCheck].getOccupant();
            if(occupant instanceof CarPart carPart) {
                if(carPart.getOwnerCar() != car) {
                    return occupant;
                }
            }
            else if(occupant instanceof Wall) {
                return occupant;
            }
        }
        return new ClearObstacle();
    }

    private Obstacle obstacleInPath(Car car, int steps, int startingX, int startingY, int xMul, int yMul) {
        if(wouldMoveOutsidePerimeter(steps, startingX, startingY, xMul, yMul)) {
            return new Wall();
        }
        if(blockingPartsMakeWall(car, steps, startingX, startingY, xMul, yMul)) {
            return new Wall();
        }
        return blockingObstacle(car, steps, startingX, startingY, xMul, yMul);
    }

    private void moveCar(Car car, int steps, int startingX, int startingY, int xMul, int yMul) {
        for(int i = 0; i < car.getSize(); i++) {
            int currentX = startingX - i * xMul;
            int currentY = startingY - i * yMul;
            int placementX = currentX + xMul * steps;
            int placementY = currentY + yMul * steps;
            Cell placementCell = cells[placementY][placementX];
            Cell currentCell = cells[currentY][currentX];
            currentCell.transferToAnotherCell(placementCell);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Cell[] row : cells) {
            for(Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrafficMap another = (TrafficMap) o;
        return toString().equals(another.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Getter
    @AllArgsConstructor
    public static class MoveDirectionAndSteps {
        private MoveDirection moveDirection;
        private int steps;
    }

}
