package com.programmersdiary.rushhour.astar.heuristics.blockChainHeuristics;

import com.programmersdiary.rushhour.cars.CarPart;

public class Edge {

    private final int destination;
    private final Node parent;
    private final Node child;

    public Edge(int destination, Node parent, Node child) {
        this.destination = destination;
        this.parent = parent;
        this.child = child;
    }

    public Edge(int destination, Node parent, CarPart carPart) {
        this.destination = destination;
        this.parent = parent;
        child = new Node(carPart, this);
    }

    public int getDestination() {
        return destination;
    }

    public Node getParent() {
        return parent;
    }

    public Node getChild() {
        return child;
    }
}
