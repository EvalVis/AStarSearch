package ev.projects.heuristics;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AStarObject<T> {

    private final AStarObject<T> parent;
    @Getter
    private final int gValue;
    private final String move;

    public AStarObject(AStarObject<T> parent, String move) {
        this.parent = parent;
        this.move = move;
        gValue = Objects.nonNull(parent) ? (parent.gValue + 1) : 0;
    }

    public abstract List<T> getCurrentStateData();

    public abstract boolean isSolved();

    public abstract Set<AStarObject<T>> getNeighbours();

    public String getMoveSequence() {
        List<String> moves = new ArrayList<>();
        AStarObject<T> current = this;
        while(Objects.nonNull(current)) {
            moves.add(current.move);
            current = current.parent;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Total moves: ");
        sb.append(moves.size() - 1); // Because first instance does not have a move.
        sb.append(System.lineSeparator());
        for(int i = moves.size() - 1; i > -1; i--) {
            sb.append(moves.get(i));
            sb.append(", ");
        }
        sb.append("END");
        return sb.toString();
    }

    public abstract boolean equals(Object other);
    public abstract int hashCode();


}
