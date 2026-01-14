# A* Search Library

An implementation of the [A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm) with multiple [variants](https://en.wikipedia.org/wiki/A*_search_algorithm#Variants) and predefined Heuristics for games like: maze, 15 Puzzle, Rush hour.

[Bachelor thesis of this library in Lithuanian language.](https://www.lvb.lt/discovery/fulldisplay?docid=alma9912936942808451&context=L&vid=370LABT_NETWORK:ELABA_UNION&lang=lt&search_scope=ELABA&adaptor=Local%20Search%20Engine&tab=ELABA&query=any,contains,Evaldas%20Visockas&offset=0)

## 🏗️ Project Structure

A multi-module Maven project with the following components:

### Core Library (`AStar/`)

The foundation library providing multiple A* algorithm implementations and framework for custom heuristics.

### Problem Implementations

- **`Maze/`** - Maze solving with image-based input/output and weighted search support
- **`Puzzle15/`** - 15-puzzle solver with heuristics and puzzle read/write from file  
- **`RushHour/`** - Rush Hour puzzle solver with advanced heuristics

## 🚀 Quick Start

### Building the Project

```bash
mvn clean install
```

### Using the Core Library

1. **Implement your problem state** by extending `AStarObject<T>`:

```java
public class MyProblemObject extends AStarObject<MyState> {
    // Implement required methods: getCurrentStateData(), isSolved(), getNeighbours()
}
```

2. **Create a heuristic** by implementing `AStarHeuristic<T>`:

```java
public class MyHeuristic implements AStarHeuristic<MyState> {
    public int calculateValue(AStarObject<MyState> aStarObject) {
        // Your heuristic calculation
    }
}
```

3. **Solve your problem**:

```java
// Choose your A* search variant
AStarVariant<MyState> variant = new ClassicAStarVariant<>(new MyHeuristic());

// Create solver and solve
AStarSolver<MyState> solver = new AStarSolver<>(startingObject, variant);
AStarObject<MyState> solution = solver.solve();
```

## 🔧 A* Algorithm Variants

### Classic A* (`ClassicAStarVariant`)
Standard A* implementation with f(n) = g(n) + h(n)

### Weighted A* (`WeightedAStarVariant`) 

Weighted variant with f(n) = g(n) + λ × h(n), supporting:
- **Constant weights** (`ConstantLFunction`)
- **Time-based weights**:
  - Linear decay (`LinearTimedLFunction`)
  - Quadratic decay (`QuadraticTimedLFunction`)

### ARA* (`ARAVariant`)

Anytime Repairing A* for iterative improvement of solutions.

Useful if you have an environment which can crash anytime.
With this algorithm you can first get a fast but not optimal solution and iterate to improve it to the optimal one.
In case of crash you have the best solution already found.

## 🧩 Problem Implementations

### Maze Solver

**Features:**
- **Image-based I/O**: Load mazes from PNG images
- **Weighted pathfinding**: Different colors represent different movement costs
- **Visual solution display**: Generates solution images with path highlighted
- **Manhattan distance heuristic**

**Color Coding for Mazes:**
- **Black** (`#000000`): Walls (impassable)
- **White** (`#FFFFFF`): Cheapest path (cost: 1)
- **Green** (`#00FF00`): Start/Goal markers (cost: 2)
- **Magenta** (`#FF00FF`): Light cost path (cost: 3)
- **Yellow** (`#FFFF00`): Medium cost path (cost: 4)
- **Blue** (`#0000FF`): High cost path (cost: 5)
- **Brown** (`#7F3300`): Highest cost path (cost: 6)

**Usage:**
```java
// Load maze from image
MazeAStarObject mazeObject = MazeReader.read("path/to/maze.png");

// Solve using weighted A*
AStarVariant<Maze> variant = new WeightedAStarVariant<>(
    new ManhattanDistanceHeuristic(), 
    new ConstantLFunction(1.2)
);
AStarSolver<Maze> solver = new AStarSolver<>(mazeObject, variant);
MazeAStarObject solution = solver.solve();

// Display solution
MazeDisplayer.displaySolution("path/to/maze.png", solution);
```

**Sample Mazes:**
The project includes various maze examples in `Maze/src/main/resources/mazes/`:
- `examplemaze.png`, `examplemaze2.png` - Simple test mazes
- `cleanmaze1.png` through `cleanmaze4.png` - Complex mazes of varying sizes
- `maze4.png` - Maze with different weights

### 15-Puzzle Solver

**Features:**
- **Multiple heuristics**:
  - Manhattan Distance (`ManhattanDistanceHeuristic`)
  - Misplaced Tiles (`MisplacedTilesHeuristic`) 
  - Inversion Distance (`InversionDistanceHeuristic`)
  - Combined ID/MD (`IDorMDHeuristic`)
- **File I/O**: Save/load puzzles and puzzle sets
- **Random puzzle generation** with solvability guarantee

**Usage:**
```java
// Create or load puzzle
Puzzle puzzle = new Puzzle(4); // 4x4 puzzle
// or load from file: PuzzleFileStorage.readPuzzlesFromFile("puzzles.txt")

// Solve
PuzzleAStarObject puzzleObject = new PuzzleAStarObject(puzzle);
AStarVariant<int[]> variant = new ClassicAStarVariant<>(new ManhattanDistanceHeuristic());
AStarSolver<int[]> solver = new AStarSolver<>(puzzleObject, variant);
```

**File Operations:**
```java
// Generate and save puzzle set
PuzzleFileStorage.generatePuzzlesAndWriteToFile(100, 4, "puzzles.txt");

// Load puzzles
List<Puzzle> puzzles = PuzzleFileStorage.readPuzzlesFromFile("puzzles.txt");
```

### Rush Hour Solver

**Features:**
- **Multiple heuristics**
- **Traffic map parsing** 

## 📊 Algorithm analysis

The library supports A* search analysis through:
- **Move sequence tracking**: Complete solution paths
- **Cost analysis**: G-value tracking for solution quality
- **Variant comparison**: Test different A* variants on the same problem

## 🔍 Different A* Variants

```java
// Classic A*
AStarVariant<Maze> classic = new ClassicAStarVariant<>(new ManhattanDistanceHeuristic());

// Weighted A* with constant weight
AStarVariant<Maze> weighted = new WeightedAStarVariant<>(
    new ManhattanDistanceHeuristic(), 
    new ConstantLFunction(1.5)
);

// Weighted A* with time-based decay
AStarVariant<Maze> timedWeighted = new WeightedAStarVariant<>(
    new ManhattanDistanceHeuristic(),
    new LinearTimedLFunction(2.0, 1.0, 100) // start=2.0, end=1.0, steps=100
);

// To make best use of A* star variants you can compare then by e.g. calculating execution time and optimal solution and comparing them.
```

### Custom Heuristics
The library supports any admissible (never overestimating) heuristic. Examples include:
- **Distance-based**: Manhattan, Euclidean
- **Problem-specific**: Misplaced tiles, car blocking analysis
- **Composite**: Combine multiple heuristics (max, weighted sum)

## 📋 Requirements

- **Java 17+**
- **Maven 3.9.9+**
- **For maze visualization**: Java Desktop support

## 📈 Tips

1. **Choose appropriate heuristics**: More informed heuristics reduce search space
2. **Use weighted A star search** for faster (suboptimal) solutions
3. **Consider ARA star search** when you need a quick solution quick but value optimal solution later
4. **Leverage time-based weights** for problems with time constraints

## 🤝 Contributing

This library provides a solid foundation for A* search implementations. To extend you can:

1. Add new problem domains in separate modules
2. Implement domain-specific heuristics
3. Add visualization utilities for new problem types

Also, you can implementations of more A* variants.

## 📄 License

Please view `LICENSE` file.

hello from ai mcp
