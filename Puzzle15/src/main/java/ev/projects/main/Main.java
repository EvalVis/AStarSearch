package ev.projects.main;

import ev.projects.astar.aStarObjects.PuzzleAStarObject;
import ev.projects.astar.heuristics.ManhattanDistanceHeuristic;
import ev.projects.puzzle.Puzzle;
import ev.projects.search.AStarSolver;
import ev.projects.variants.classicAStar.ClassicAStarVariant;
import ev.projects.variants.weightedAStar.WeightedAStarVariant;
import ev.projects.variants.weightedAStar.weights.ConstantLFunction;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle("7,5,2,1,4,10,6,12,3,15,11,13,8,9,14,-1");
        //Puzzle puzzle = new Puzzle(3);
        //Puzzle puzzle = new Puzzle("2,3,1,-1");
        //Puzzle puzzle = new Puzzle("7,3,1,5,6,2,4,8,-1");
        System.out.println(puzzle);
        PuzzleAStarObject puzzleAStarObject = new PuzzleAStarObject(puzzle);
        AStarSolver<int[]> solver = new AStarSolver<>(puzzleAStarObject,
                new WeightedAStarVariant<>(new ManhattanDistanceHeuristic(), new ConstantLFunction(1)));
        String solution = solver.solve();
        System.out.println(solution);
    }

}
