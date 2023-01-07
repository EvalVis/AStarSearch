package ev.projects.utils;

import ev.projects.puzzle.BlankCell;
import lombok.Getter;

public class Utils {

    public static int getInversionCountLeftRight(int[] array)
    {
        int inversionCount = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if(canIncreaseInversionCount(i, j, array)) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    public static int getInversionCountTopBottom(int[] array)
    {
        int inversionCount = 0;
        for (int i = 0; i < array.length - 1; i++) {
            int currentPosition = getTopBottomPosition(i, (int) Math.sqrt(array.length));
            for (int j = i + 1; j < array.length; j++) {
                int nextPosition = getTopBottomPosition(j, (int) Math.sqrt(array.length));
                if(canIncreaseInversionCount(currentPosition, nextPosition, array)) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    private static boolean canIncreaseInversionCount(int currentPosition, int nextPosition, int[] array) {
        if(array[currentPosition] == BlankCell.blankCellMarker ||array[nextPosition] == BlankCell.blankCellMarker) {
            return false;
        }
        return array[currentPosition] > array[nextPosition];
    }

    private static int getTopBottomPosition(int currentLeftRightPosition, int lineSize) {
        return (currentLeftRightPosition / lineSize) + lineSize * (currentLeftRightPosition % lineSize);
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

    @Getter
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

    }


}
