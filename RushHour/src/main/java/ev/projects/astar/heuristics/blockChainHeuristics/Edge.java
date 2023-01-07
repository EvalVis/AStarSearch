package ev.projects.astar.heuristics.blockChainHeuristics;

import ev.projects.cars.CarPart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Edge {

    private final int destination;
    private final Node parent;
    private final Node child;

    public Edge(int destination, Node parent, CarPart carPart) {
        this.destination = destination;
        this.parent = parent;
        child = new Node(carPart, this);
    }
}
