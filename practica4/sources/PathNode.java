package practica_4;

import java.util.ArrayList;

public class PathNode implements Comparable<PathNode>{
    
    private ArrayList<String> res;
    private int visitedVertex;
    private double totalCost;
    private double estimatedCost;
    private double minEdgeValue;
    
    public PathNode(String vertexToVisit) {
        res = new ArrayList<String>();
        res.add(vertexToVisit);
        visitedVertex = 1;
        totalCost = 0.0;
		estimatedCost = numberOfVertex() * getMinEdgeValue();
    }
    
    public double getMinEdgeValue() {
		return this.minEdgeValue;
	}
    
    public void setMinEdgeValue(double minEdgeValue) {
		this.minEdgeValue = minEdgeValue;
	}

	private int numberOfVertex() {
		return this.res.size();
	}

	public PathNode(PathNode parentPathNode) {
        this.res = new ArrayList<String>(parentPathNode.res);
        this.visitedVertex = parentPathNode.visitedVertex;
        this.totalCost = parentPathNode.totalCost;
        this.estimatedCost = parentPathNode.estimatedCost;
    }
    
    public ArrayList<String> getRes(){
        return this.res;
    }
    
    public void addVertexRes(String vertex) {
        this.res.add(vertex);
    }
    
    public String lastVertexRes() {
        return this.res.get(this.res.size() - 1);
    }
    
    public boolean isVertexVisited(String vertex) {
        return this.res.contains(vertex);
    }
    
    public int getVisitedVertices() {
        return visitedVertex;
    }
    
    public void setVisitedVertices(int visitedVertices) {
        this.visitedVertex = visitedVertices;
    }
    
    public double getTotalCost() {
        return this.totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public double getEstimatedCost() {
        return estimatedCost;
    }
    
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
    
    public int compareTo(PathNode p) {
        return Double.compare(this.estimatedCost, p.estimatedCost);
    }
}