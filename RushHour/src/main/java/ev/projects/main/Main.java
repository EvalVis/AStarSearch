package ev.projects.main;

import ev.projects.astar.aStarObjects.TrafficMapAStarObject;
import ev.projects.astar.heuristics.ExitBlockingCarsCountHeuristic;
import ev.projects.search.AStarSolver;
import ev.projects.search.MoveSequence;
import ev.projects.trafficMap.TrafficMap;
import ev.projects.variants.classicAStar.ClassicAStarVariant;

import static ev.projects.utils.TrafficMapParser.parse6on6Map;

public class Main {

    public static void main(String[] args) {
        TrafficMap trafficMap = parse6on6Map("IBBxooIooLDDJAALooJoKEEMFFKooMGGHHHM");
        System.out.println(trafficMap);
        AStarSolver<TrafficMap> solver = new AStarSolver<>(
                new TrafficMapAStarObject(new MoveSequence(null, "START"), 0, trafficMap),
                new ClassicAStarVariant<>(new ExitBlockingCarsCountHeuristic()));
        System.out.println(solver.solve());
    }

}
