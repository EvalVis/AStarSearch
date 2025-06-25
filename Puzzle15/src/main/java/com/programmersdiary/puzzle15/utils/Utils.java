package com.programmersdiary.puzzle15.utils;

import com.programmersdiary.puzzle15.puzzle.BlankCell;

public class Utils {

    public static Inversions getInversions(int[] array)
    {
        int puzzleHeight = (int) Math.sqrt(array.length);
        int horizontalInversionCount = 0;
        int verticalInversionCount = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if(canIncreaseInversionCount(i, j, array)) {
                    horizontalInversionCount++;
                }
                if(canIncreaseInversionCount(
                        getVerticalPosition(i, puzzleHeight),
                        getVerticalPosition(j, puzzleHeight),
                        array
                )
                ) {
                    verticalInversionCount++;
                }
            }
        }
        return new Inversions(horizontalInversionCount, verticalInversionCount);
    }

    private static int getVerticalPosition(int index, int puzzleHeight) {
        return (index / puzzleHeight) + (puzzleHeight * (index % puzzleHeight));
    }

    public static boolean canIncreaseInversionCount(int firstPosition, int secondPosition, int[] array) {
        return array[firstPosition] != BlankCell.blankCellMarker
                && array[secondPosition] != BlankCell.blankCellMarker
                && array[firstPosition] > array[secondPosition];
    }

    // Counted from bottom row
    public static boolean blankCellIsOnEvenRow(BlankCell blankCell, int edgeSize) {
        int y = blankCell.getPosition() / edgeSize;
        int yFromBottom = edgeSize - y;
        return yFromBottom % 2 == 0;
    }

    // Starting from -2 because last cell is blank.
    public static void inverseOnePair(int[] array) {
        int temp = array[array.length - 2];
        array[array.length - 2] = array[array.length - 3];
        array[array.length - 3] = temp;
    }

    public static boolean isLastRow(int[] array, int position) {
        return position + Math.sqrt(array.length) >= array.length;
    }

    public static boolean isFirstRow(int[] array, int position) {
        return position - Math.sqrt(array.length) < 0;
    }

    public static class Inversions {
        private final int horizontalInversions;
        private final int verticalInversions;

        public Inversions(int horizontalInversions, int verticalInversions) {
            this.horizontalInversions = horizontalInversions;
            this.verticalInversions = verticalInversions;
        }

        public int getHorizontalInversions() {
            return horizontalInversions;
        }

        public int getVerticalInversions() {
            return verticalInversions;
        }
    }

    public static class StartEndCoordinates {
        private final int startX;
        private final int startY;
        private int endX;
        private int endY;

        public StartEndCoordinates(int currentPosition, int lineSize) {
            startX = currentPosition % lineSize;
            startY = currentPosition / lineSize;
        }

        public static StartEndCoordinates getCoordinates(int currentPosition, int currentValue, int lineSize) {
            if(currentValue == -1) {
                return getCoordinatesOfEmptyCell(currentPosition, lineSize);
            }
            return getCoordinatesOfNumberedCell(currentPosition, currentValue, lineSize);
        }

        private static StartEndCoordinates getCoordinatesOfEmptyCell(int currentPosition, int lineSize) {
            StartEndCoordinates coordinates = new StartEndCoordinates(currentPosition, lineSize);
            coordinates.endX = lineSize - 1;
            coordinates.endY = lineSize - 1;
            return coordinates;
        }

        private static StartEndCoordinates getCoordinatesOfNumberedCell(int currentPosition, int currentValue, int lineSize) {
            StartEndCoordinates coordinates = new StartEndCoordinates(currentPosition, lineSize);
            coordinates.endX = (currentValue - 1) % lineSize;
            coordinates.endY = (currentValue - 1) / lineSize;
            return coordinates;
        }

        public int getStartX() {
            return startX;
        }

        public int getStartY() {
            return startY;
        }

        public int getEndX() {
            return endX;
        }

        public int getEndY() {
            return endY;
        }
    }


}
