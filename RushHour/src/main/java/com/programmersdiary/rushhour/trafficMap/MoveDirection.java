package com.programmersdiary.rushhour.trafficMap;

/**
 * FORWARD - If HORIZONTAL: RIGHT, if VERTICAL: DOWN
 * BACKWARDS - If HORIZONTAL: LEFT, if VERTICAL: UP
 */
public enum MoveDirection {

    FORWARD(1), BACKWARDS(-1);
    
    private final int moveSign;

    MoveDirection(int moveSign) {
        this.moveSign = moveSign;
    }

    public int getMoveSign() {
        return moveSign;
    }
}
