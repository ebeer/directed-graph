package com.ebeer.graph;

class Edge {
	private int weight;
	private Vertex origin;
	private Vertex destination;
	
	protected Edge(Vertex origin, Vertex destination, int weight){
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		
	}
	
	
	protected int getWeight(){
		return this.weight;
	}
	
	
	protected Vertex getOrigin(){
		return this.origin;
	}
	
	
	protected Vertex getDestination(){
		return this.destination;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer(this.origin.getName());
		str.append(this.destination.getName());
		str.append(this.weight);
		return str.toString();
	}

}
