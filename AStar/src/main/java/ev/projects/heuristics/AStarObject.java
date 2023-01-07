package ev.projects.heuristics;
import ev.projects.search.MoveSequence;
import lombok.Getter;

import java.util.Set;

public abstract class AStarObject<T> {
    @Getter
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


}
