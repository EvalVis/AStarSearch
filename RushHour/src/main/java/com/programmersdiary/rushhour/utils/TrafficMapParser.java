package com.programmersdiary.rushhour.utils;

import com.programmersdiary.rushhour.cars.Car;
import com.programmersdiary.rushhour.cars.CarDirection;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;

import java.util.HashMap;

import static com.programmersdiary.rushhour.cars.CarFactory.createCar;
import static com.programmersdiary.rushhour.cars.CarFactory.createMainCar;

public class TrafficMapParser {

    public static TrafficMap parse6on6Map(String notation) {
        StringBuilder correctFormatBuilder = new StringBuilder();
        for(int y = 0; y < 6; y++) {
            int rowStart = y * 6;
            correctFormatBuilder.append(notation, rowStart, rowStart + 6);
            correctFormatBuilder.append("\n");
        }
        return parseMap(correctFormatBuilder.toString(), 'o');
    }

    public static TrafficMap parseMap(String notation, char emptySignature) {
        HashMap<Character, CarData> data = new HashMap<>();
        String[] rows = notation.split("\n");
        int verticalSize = rows.length;
        int horizontalSize = rows[0].length();
        TrafficMap trafficMap = new TrafficMap(horizontalSize, verticalSize, 4);
        for(int y = 0; y < verticalSize; y++) {
            for(int x = 0; x < horizontalSize; x++) {
                char identifier = rows[y].charAt(x);
                if(identifier == 'x') {
                    trafficMap.placeWallOnMap(y, x);
                }
                else if(identifier != emptySignature) {
                    translateIdentifierToData(identifier, data, x, y);
                }
            }
        }
        placeCarsOnMap(trafficMap, data);
        return trafficMap;
    }

    private static void placeCarsOnMap(TrafficMap trafficMap, HashMap<Character, CarData> data) {
        for(Character identifier : data.keySet()) {
            CarData carData = data.get(identifier);
            Car car;
            if(identifier == 'A') {
                car = createMainCar(carData.direction, carData.size);
            }
            else {
                car = createCar(carData.direction, identifier, carData.size);
            }
            trafficMap.placeCarOnMap(car, carData.y, carData.x);
        }
    }

    private static void translateIdentifierToData(Character identifier, HashMap<Character, CarData> data, int x, int y) {
        if(data.containsKey(identifier)) {
            CarData carData = data.get(identifier);
            carData.increaseSize();
            carData.setDirection(x);
        }
        else {
            data.put(identifier, new CarData(x, y));
        }
    }

    private static class CarData {
        private final int x;
        private final int y;
        private int size = 1;
        private CarDirection direction = CarDirection.VERTICAL;

        public CarData(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void increaseSize() {
            size++;
        }

        public void setDirection(int endX) {
            if(x == endX) {
                direction = CarDirection.VERTICAL;
            }
            else {
                direction = CarDirection.HORIZONTAL;
            }
        }

    }

}
