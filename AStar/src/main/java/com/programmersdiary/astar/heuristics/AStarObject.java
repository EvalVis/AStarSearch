package com.programmersdiary.astar.heuristics;
import com.programmersdiary.astar.search.MoveSequence;

import java.util.Set;

public abstract class AStarObject<T> {
    protected final int gValue;
    protected final MoveSequence moveSequence;

    public AStarObject(MoveSequence moveSequence, int gValue) {
        this.moveSequence = moveSequence;
        this.gValue = gValue;
    }

    public abstract T getCurrentStateData();

    public abstract boolean isSolved();

    public abstract Set<AStarObject<T>> getNeighbours();

    public String getSequenceOfMoves() {
        return moveSequence.toString();
    }

    public abstract boolean equals(Object other);
    public abstract int hashCode();

    public MoveSequence getMoveSequence() {
        return moveSequence;
    }

    public int getGValue() {
        return gValue;
    }
}
