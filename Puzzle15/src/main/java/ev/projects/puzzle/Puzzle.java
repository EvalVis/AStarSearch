package ev.projects.puzzle;

import ev.projects.utils.Utils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle {

    @Getter
    private final int[] cells;
    private final int size;
    @Getter
    private final BlankCell blankCell;

    public Puzzle(Puzzle puzzle) {
        size = puzzle.size;
        cells = new int[size * size];
        System.arraycopy(puzzle.cells, 0, cells, 0, cells.length);
        blankCell = new BlankCell(puzzle.blankCell);
    }

    public Puzzle(int size) {
        this.size = size;
        cells = new int[size * size];
        blankCell = new BlankCell(size * size - 1);
        generateRandomSolvablePuzzle();
    }

    public Puzzle(String puzzle) {
        String[] parts = puzzle.split(",");
        size = (int) Math.sqrt(parts.length);
        cells = new int[size * size];
        blankCell = new BlankCell(
                IntStream.range(0, parts.length)
                        .filter(i-> parts[i].equals("-1"))
                        .findFirst()
                        .getAsInt()
        );
        for(int i = 0; i < parts.length; i++) {
            cells[i] = Integer.parseInt(parts[i]);
        }
    }

    private void generateRandomSolvablePuzzle() {
        cells[cells.length - 1] = BlankCell.blankCellMarker;
        List<Integer> numbersToShuffle = new ArrayList<>();
        for(int i = 1; i <= cells.length - 1; i++) {
            numbersToShuffle.add(i);
        }
        Collections.shuffle(numbersToShuffle);
        for(int i = 0; i < cells.length - 1; i++) {
            cells[i] = numbersToShuffle.get(i);
        }
        cells[cells.length - 1] = BlankCell.blankCellMarker;
        if(!isSolvable()) {
            Utils.inverseOnePair(cells);
        }
    }

    public boolean isSolved() {
        int expectedValueIfSolved = 1;
        for(int i = 0; i < cells.length - 1; i++) {
            if (cells[i] != expectedValueIfSolved) {
                return false;
            }
            expectedValueIfSolved++;
        }
        return true;
    }

    public boolean isSolvable() {
        int inversionCount = Utils.getInversions(cells).getKey();
        if(size % 2 == 0 && Utils.blankCellIsOnEvenRow(blankCell, size)) {
            return inversionCount % 2 != 0;
        }
        return inversionCount % 2 == 0;
    }

    public String toStringSeparateWithCommas() {
        if(cells.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int cell : cells) {
            sb.append(cell);
            sb.append(",");
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                sb.append(cells[y * size + x]);
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle = (Puzzle) o;
        return Arrays.equals(cells, puzzle.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cells);
    }

}
