package com.programmersdiary.astar.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record MoveSequence(com.programmersdiary.astar.search.MoveSequence parent, String move) {

    @Override
    public String toString() {
        List<String> moves = new ArrayList<>();
        MoveSequence current = this;
        while (Objects.nonNull(current)) {
            moves.add(current.move);
            current = current.parent;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Total moves: ");
        sb.append(moves.size() - 1);
        sb.append(System.lineSeparator());
        for (int i = moves.size() - 1; i > -1; i--) {
            sb.append(moves.get(i));
            sb.append(", ");
        }
        sb.append("END");
        return sb.toString();
    }
}
