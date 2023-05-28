package ev.projects.astar.heuristics;

import ev.projects.astar.heuristics.blockChainHeuristics.BlockChainHeuristic;
import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.trafficMap.TrafficMap;

public class ExitOrBlockChain1DistanceHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public ExitOrBlockChain1DistanceHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        TrafficMap trafficMap = aStarObject.getCurrentStateData();
        if(trafficMap.getFinishX() - trafficMap.getMainCar().getFrontX() == 1) {
            return new BlockChainHeuristic(maxMoveCount).calculateValue(aStarObject);
        }
        return new ExitBlockingCarsCountHeuristic().calculateValue(aStarObject);
    }
}
