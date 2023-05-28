package ev.projects.astar.heuristics;

import ev.projects.astar.heuristics.blockChainHeuristics.BlockChainHeuristic;
import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.trafficMap.TrafficMap;

public class MaxExitAndBlockChainHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public MaxExitAndBlockChainHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        return Math.max(
                new ExitBlockingCarsCountHeuristic().calculateValue(aStarObject),
                new BlockChainHeuristic(maxMoveCount).calculateValue(aStarObject)
        );
    }
}
