package ev.projects.astar.aStarObjects;

import ev.projects.heuristics.AStarObject;
import ev.projects.puzzle.BlankCell;
import ev.projects.puzzle.Puzzle;
import ev.projects.search.MoveSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PuzzleAStarObject extends AStarObject<int[]> {

    private final Puzzle puzzle;

    public PuzzleAStarObject(MoveSequence moveSequence, int gValue, Puzzle puzzle) {
        super(moveSequence, gValue);
        this.puzzle = puzzle;
    }

    public PuzzleAStarObject(Puzzle puzzle) {
        super(new MoveSequence(null, "START"), 0);
        this.puzzle = puzzle;
    }

    @Override
    public int[] getCurrentStateData() {
        return puzzle.getCells();
    }

    @Override
    public boolean isSolved() {
        return puzzle.isSolved();
    }

    @Override
    public Set<AStarObject<int[]>> getNeighbours() {
        Set<AStarObject<int[]>> neighbours = new HashSet<>();
        BlankCell blankCell = puzzle.getBlankCell();
        int[] cells = puzzle.getCells();
        if(blankCell.canMoveFromTop(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromTop(copiedPuzzle.getCells());
            addNeighbour(neighbours, numberMoved, copiedPuzzle);
        }
        if(blankCell.canMoveFromBottom(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromBottom(copiedPuzzle.getCells());
            addNeighbour(neighbours, numberMoved, copiedPuzzle);
        }
        if(blankCell.canMoveFromLeft(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromLeft(copiedPuzzle.getCells());
            addNeighbour(neighbours, numberMoved, copiedPuzzle);
        }
        if(blankCell.canMoveFromRight(cells)) {
            Puzzle copiedPuzzle = new Puzzle(puzzle);
            int numberMoved = copiedPuzzle.getBlankCell().moveFromRight(copiedPuzzle.getCells());
            addNeighbour(neighbours, numberMoved, copiedPuzzle);
        }
        return neighbours;
    }

    private void addNeighbour(Set<AStarObject<int[]>> neighbours, int numberMoved, Puzzle copiedPuzzle) {
        neighbours.add(new PuzzleAStarObject(new MoveSequence(moveSequence,
                Integer.toString(numberMoved)), gValue + 1, copiedPuzzle));
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
