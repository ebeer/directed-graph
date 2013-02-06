package com.ebeer.graph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class DirectedGraph {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	public static int DISTANCE_ERROR_CASE = -1;
	
	public DirectedGraph(){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
		
	public Vertex getVertexByName(String vertexName) {
	    Vertex found = null;
	    for (Vertex vertex : this.vertices) {
	      if (vertexName.equals(vertex.getName())) {
	        found = vertex;
	        break;
	      }
	    }
	    return found;
	  }
	
	
	public Vertex addVertex (String vertexName){
		Vertex newVertex = this.getVertexByName(vertexName);
		if (newVertex == null){
			newVertex = new Vertex(vertexName);
			this.vertices.add(newVertex);
		}
		return newVertex;	
	}
	
	
	public boolean addEdge(String strStart, String strEnd,  int weight) {
		
		Vertex start = this.addVertex(strStart);
		Vertex end = this.addVertex(strEnd);
		
	    Edge edge = new Edge(start, end, weight);
	    
	    //return false if any of the addition operations fail
	    if (start.addOutgoingEdge(edge))
	    	return edges.add(edge);
	    else
	    	return false;
	    }
	
	/*
	 * @return int number of vertices in the graph
	 */
	public int getSize(){
		return this.vertices.size();
	}
	
	/*
	 * returns distance for specified route
	 * return value of -1 indicates route not found
	 * 
	 * @param String[] path - array indicating route to follow
	 * @return int -sum of weights for the edges on the indicated path
	 */
	public int getDistanceForRoute(String[] path){
		int distance =0;
		Vertex current, next = null;
		Edge currentPath = null;
		
		for (int i=0; i < path.length - 1; i++){
			current = this.getVertexByName(path[i]);
			next = this.getVertexByName(path[i+1]);
			if ((current != null) & (next != null)){	
				currentPath = current.findOutgoingEdge(next);
				if (currentPath != null)
					distance += currentPath.getWeight();
				else
					return DISTANCE_ERROR_CASE; //no edge connecting these points
			}
			else 
				return DISTANCE_ERROR_CASE; //error finding the vertices, path not found
			}
				
		return distance;
	}
	
	
	/*
	 * returns the number of trips that can be made from
	 * the start to end points up to the indicated number of stops
	 * 
	 * @param string - name of starting vertex
	 * @param string - name of ending vertex
	 * @param int maximum number of stops
	 * 
	 * @return int number of routes found.
	 * 
	 */
	public int getNumberOfRoutesWithMaxStops(String strStart, String strEnd, int maxStops){
		int trips = 0;
		Vertex start, end, next= null;
		ArrayList<Edge> edges = null;
		
		start = this.getVertexByName(strStart);
		end = this.getVertexByName(strEnd);
		edges = start.getOutgoingEdges();
		for (int i = 0; i< edges.size(); i++){
			next = edges.get(i).getDestination();
			if (next == end){
				trips +=1;
			}
			else{
				if (maxStops > 1)
					trips += getNumberOfRoutesWithMaxStops(next.getName(), strEnd, (maxStops -1));
			}
		}
		return trips;
	}
	
	/*
	 * returns number of routes between the indicated start and end Vertices that
	 * have the exact number of stops indicated
	 * 
	 * @param string - name of starting Vertex
	 * @param string - name of ending Vertex
	 * @param int - number of stops
	 * 
	 * @return integer value of the number of stops found
	 */
	public int getNumberOfRoutesWithExactStops(String strStart, String strEnd, int stops){
		int trips = 0;
		Vertex start, end, next= null;
		ArrayList<Edge> edges = null;
		
		start = this.getVertexByName(strStart);
		end = this.getVertexByName(strEnd);
		edges = start.getOutgoingEdges();
		for (int i = 0; i< edges.size(); i++){
			next = edges.get(i).getDestination();
			if ((next == end) & (stops == 1)){
				trips +=1;
			}
			else{
				if (stops > 1)
					trips += getNumberOfRoutesWithExactStops(next.getName(), strEnd, (stops -1));
			}
		}
		return trips;
	}
	/*
	 * returns the number of trips that can be made from
	 * the start to end points with less than (but not including)the indicated total length
	 * 
	 * @param String strStart - name of starting Vertex
	 * @param String strEnd - name of ending vertex
	 * @param int maxLengthValue
	 * 
	 * @return int - number of routes found
	 * 
	 */
	public int getNumberOfRoutesWithLengthLessThan(String strStart, String strEnd, int maxLength){
		int trips = 0;
		int length = 0;
		Vertex start, end, next = null;
		ArrayList<Edge> edges = null;
		
		start = this.getVertexByName(strStart);
		end = this.getVertexByName(strEnd);
		edges = start.getOutgoingEdges();
		
		for (int i = 0; i< edges.size(); i++){
			
			next = edges.get(i).getDestination();
			length = edges.get(i).getWeight();
			if ((next == end) & (length < maxLength)){
				trips +=1;
			}
			//allowing for the trips to read the end point multiple times while still under max length
			if ((maxLength - length) > 0){
				trips += getNumberOfRoutesWithLengthLessThan(next.getName(), strEnd, (maxLength -length));
			}
			
		}
		return trips;
	}
	
	
	/*
	 * returns integer value of shortest distance of trip between indiated vertices
	 * does not allow 0 length trips
	 * 
	 * @param String start - name of starting vertex
	 * @param String end - name of ending vertex
	 */	
	public int getShortestDistance(String start, String end){
		Vertex temp;
		int distance;
		ArrayList<Integer> distances = new ArrayList<Integer>();
		
		if (start != end ){
			computePaths(this.getVertexByName(start));
			return this.getVertexByName(end).getMinDistance();
		}else{
			//special case handling that forces you to not use a zero length path
			Vertex vStart = this.getVertexByName(start);
			ArrayList<Edge> edges = vStart.getOutgoingEdges();
			for (int i = 0; i< edges.size(); i++){
				temp = edges.get(i).getDestination();
				distance = edges.get(i).getWeight();
				computePaths(temp);
				distances.add(distance + this.getVertexByName(end).getMinDistance());
			}
			if (!distances.isEmpty())
				return Collections.min(distances);
			else
				return 0;
			
		}
		
	}
	
	/*
	 * uses Dijkstra's algorithm to compute shortest paths from start to all points in the graph
	 * 
	 * @param Vertex start
	 * 
	 */
	public static void computePaths(Vertex start)
    {
	   start.minDistance= 0;
	   PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
	   vertexQueue.add(start);
	
		while (!vertexQueue.isEmpty()) {
		    Vertex current = vertexQueue.poll();
	
	            // Visit each edge exiting current Vertex
	            for (Edge e : current.getOutgoingEdges())
	            {
	                Vertex next = e.getDestination();
	                int weight = e.getWeight();
	                int distanceThroughPoint = current.getMinDistance() + weight;
					if (distanceThroughPoint < next.getMinDistance()) {
					    vertexQueue.remove(next);
					    next.setMinDistance(distanceThroughPoint) ;
					    next.setShortestPrevious(e);
					    vertexQueue.add(next);
					}
	            }
        	}
    }
	
	/*
	 * returns a String representation of the Graph for testing purposes
	 * @return String
	 */
	public String toString(){
		//calls vertex.toString for each Vertex
		//which includes all incoming and outgoing edges
		StringBuffer str = new StringBuffer("Vertices= \n");
		for (int i = 0; i < vertices.size(); i++){
			str.append(vertices.get(i).toString());
		}
		return str.toString();
		
	}
	
}
