package ev.projects.trafficMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FORWARD - If HORIZONTAL: RIGHT, if VERTICAL: DOWN
 * BACKWARDS - If HORIZONTAL: LEFT, if VERTICAL: UP
 */
@Getter
@AllArgsConstructor
public enum MoveDirection {
    FORWARD(1), BACKWARDS(-1);
    private final int moveSign;

}
