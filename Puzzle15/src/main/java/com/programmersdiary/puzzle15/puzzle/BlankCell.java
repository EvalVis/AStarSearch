package com.programmersdiary.puzzle15.puzzle;

import com.programmersdiary.puzzle15.utils.Utils;

public class BlankCell {

    public static final int blankCellMarker = -1;
    private int position;

    public BlankCell(BlankCell blankCell) {
        position = blankCell.position;
    }

    public BlankCell(int position) {
        this.position = position;
    }

    public int moveFromTop(int[] cells) {
        int topY = position - (int) Math.sqrt(cells.length);
        return move(cells, topY);
    }

    public boolean canMoveFromTop(int[] cells) {
        return !Utils.isFirstRow(cells, position);
    }

    public int moveFromBottom(int[] cells) {
        int bottomY = position + (int) Math.sqrt(cells.length);
        return move(cells, bottomY);
    }

    public boolean canMoveFromBottom(int[] cells) {
        return !Utils.isLastRow(cells, position);
    }

    public int moveFromRight(int[] cells) {
        return move(cells, position + 1);
    }

    public boolean canMoveFromRight(int[] cells) {
        return (position + 1) % Math.sqrt(cells.length) != 0;
    }

    public int moveFromLeft(int[] cells) {
        return move(cells, position - 1);
    }

    private int move(int[] cells, int blankCellDestination) {
        cells[position] = cells[blankCellDestination];
        int movedNumber = cells[blankCellDestination];
        cells[blankCellDestination] = blankCellMarker;
        position = blankCellDestination;
        return movedNumber;
    }

    public boolean canMoveFromLeft(int[] cells) {
        return position % Math.sqrt(cells.length) != 0;
    }

    public int getPosition() {
        return position;
    }
}
