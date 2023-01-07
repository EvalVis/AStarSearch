package ev.projects.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarDirection {
    HORIZONTAL(1, 0), VERTICAL(0, 1);

    private final int xMultiplier;
    private final int yMultiplier;

}
