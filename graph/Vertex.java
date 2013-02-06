package com.ebeer.graph;


import java.util.ArrayList;

class Vertex implements Comparable<Vertex>{
	private String name;
	protected Edge shortestPrevious;
	protected int minDistance = Integer.MAX_VALUE;
	private ArrayList<Edge> outgoingEdges;
	
	
	public int compareTo(Vertex other){
		return Integer.compare(minDistance, other.minDistance);
	}
	
	protected Vertex (String name){
		this.name = name;
		this.outgoingEdges = new ArrayList<Edge>();
	}
	
	protected int getMinDistance(){
		return this.minDistance;
	}
	
	protected void setMinDistance(int distance){
		this.minDistance = distance;
	}
	
	protected void setShortestPrevious(Edge e){
		this.shortestPrevious = e;
	}
	
	protected Edge getShortestPrevious(){
		return this.shortestPrevious;
	}
	
	protected String getName(){
		return this.name;
	}
	
	
	protected ArrayList<Edge> getOutgoingEdges(){
		return this.outgoingEdges;
	}
	
	
	
	/*
	 * adds Edge object to this Vertex ArrayList of outgoingEdges
	 * returns false value if the Edge has a duplicate 
	 * destination name of an exiting one
	 * 
	 * @param Edge edge - outgoing edge to add to the Vertex
	 * @return boolean - success value
	 */
	protected boolean addOutgoingEdge(Edge edge){
		if (this.findOutgoingEdge(edge.getDestination()) != null)
			return false;
		else
			return this.outgoingEdges.add(edge);
	}
	
	/*
	 * returns the Edge object attached to this Vertex
	 * that has a destination name that matches 
	 * the name of the Vertex passed as parameter
	 * 
	 * @param Vertex end
	 * @return Edge - matching edge object, null if no match
	 */
	protected Edge findOutgoingEdge(Vertex end){
		if (this.outgoingEdges != null){
			for (Edge edge : this.outgoingEdges){
				if (edge.getDestination().getName() == end.getName())
					return edge;
			}
		}
		return null;
	}
	
		
	/*
	 * returns String representation of the Vertex
	 * includes all incoming and outgoing edges
	 */
	public String toString(){
		StringBuffer str = new StringBuffer("Vertex " + this.name + "\n");
		str.append("Outgoing edges{ ");
		
		for (int i= 0; i < this.outgoingEdges.size(); i++){
			str.append(this.outgoingEdges.get(i).toString() + ", ");
		}
		str.append("}\n");
		return str.toString();
	}
}
