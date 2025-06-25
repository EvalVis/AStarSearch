package com.programmersdiary.rushhour.cars;

public class Car {

    private final CarDirection carDirection;
    private final char identifier;
    private final CarPart[] parts;
    private final boolean main;

    public Car(Car car) {
        carDirection = car.getCarDirection();
        identifier = car.getIdentifier();
        parts = new CarPart[car.getParts().length];
        main = car.isMain();
        for(int i = 0; i < parts.length; i++) {
            parts[i] = new CarPart(car.getParts()[i], this);
        }
    }

    public Car(CarDirection carDirection, char identifier, int size, boolean main) {
        this.carDirection = carDirection;
        this.identifier = identifier;
        if(size < 0) {
            throw new RuntimeException("Size must be > 0!");
        }
        parts = new CarPart[size];
        for(int i = 0; i < size; i++) {
            CarPart part = new CarPart(this, i);
            parts[i] = part;
        }
        this.main = main;
    }

    @Override
    public String toString() {
        return String.valueOf(identifier);
    }

    public int getRearX() {
        return getRearPart().getCell().getX();
    }

    public int getRearY() {
        return getRearPart().getCell().getY();
    }

    public int getFrontX() {
        return getFrontPart().getCell().getX();
    }

    public int getFrontY() {
        return getFrontPart().getCell().getY();
    }

    public CarPart getRearPart() {
        return parts[0];
    }

    public CarPart getFrontPart() {
        return parts[parts.length - 1];
    }

    public int getSize() {
        return parts.length;
    }

    public int getReversedSteps(int steps) {
        return (getSize() - 1) - steps;
    }

    public CarDirection getCarDirection() {
        return carDirection;
    }

    public char getIdentifier() {
        return identifier;
    }

    public CarPart[] getParts() {
        return parts;
    }

    public boolean isMain() {
        return main;
    }
}
