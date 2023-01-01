package ev.projects.astar.aStarObjects;

import ev.projects.heuristics.AStarObject;
import ev.projects.puzzle.BlankCell;
import ev.projects.puzzle.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PuzzleAStarObject extends AStarObject<Integer> {

    private final Puzzle puzzle;

    public PuzzleAStarObject(AStarObject<Integer> parent, String move, Puzzle puzzle) {
        super(parent, move);
        this.puzzle = puzzle;
    }

    public PuzzleAStarObject(Puzzle puzzle) {
        super(null, "START");
        this.puzzle = puzzle;
    }

    @Override
    public List<Integer> getCurrentStateData() {
        return Arrays.stream(puzzle.getCells()).boxed().collect(Collectors.toList());
    }

    @Override
    public boolean isSolved() {
        return puzzle.isSolved();
    }

    @Override
    public Set<AStarObject<Integer>> getNeighbours() {
        Set<AStarObject<Integer>> neighbours = new HashSet<>();
        BlankCell blankCell = puzzle.getBlankCell();
        int[] cells = puzzle.getCells();
        if(blankCell.canMoveFromTop(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromTop(copiedPuzzle.getCells());
            neighbours.add(new PuzzleAStarObject(this, Integer.toString(numberMoved), copiedPuzzle));
        }
        if(blankCell.canMoveFromBottom(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromBottom(copiedPuzzle.getCells());
            neighbours.add(new PuzzleAStarObject(this, Integer.toString(numberMoved), copiedPuzzle));
        }
        if(blankCell.canMoveFromLeft(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromLeft(copiedPuzzle.getCells());
            neighbours.add(new PuzzleAStarObject(this, Integer.toString(numberMoved), copiedPuzzle));
        }
        if(blankCell.canMoveFromRight(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromRight(copiedPuzzle.getCells());
            neighbours.add(new PuzzleAStarObject(this, Integer.toString(numberMoved), copiedPuzzle));
        }
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleAStarObject puzzleAStarObject = (PuzzleAStarObject) o;
        return Arrays.equals(puzzle.getCells(), puzzleAStarObject.puzzle.getCells());
    }

    @Override
    public int hashCode() {
        return puzzle.hashCode();
    }

    @Override
    public String toString() {
        return puzzle.toString();
    }
}
