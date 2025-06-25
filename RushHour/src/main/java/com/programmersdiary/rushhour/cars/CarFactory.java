package com.programmersdiary.rushhour.cars;

public class CarFactory {

    public static Car createCar(char identifier) {
        return new Car(CarDirection.HORIZONTAL, identifier, 2, false);
    }

    public static Car createCar(CarDirection carDirection, char identifier, int size) {
        return new Car(carDirection, identifier, size, false);
    }

    public static Car createMainCar(CarDirection carDirection, int size) {
        return new Car(carDirection, 'A', size, true);
    }

    public static Car createMainCar() {
        return new Car(CarDirection.HORIZONTAL, 'A', 2, true);
    }

}
