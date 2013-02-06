package com.ebeer.trains;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.ebeer.graph.DirectedGraph;

public class TrainRoutes {
	private static String ERROR_CASE = "NO SUCH ROUTE";
	
	/**
	 * prompts for entry of graph data 
	 * expects nodes A-E for the cases below.
	 * 
	 * example: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	 * @param args
	 */
    public static void main(String [] args )
    {
    	DirectedGraph trainRoutes = new DirectedGraph();
    	Pattern edgePattern = Pattern.compile("[a-zA-Z]{2}+\\d");
    	String edgeToken;
    	String start;
    	String end;
    	int weight;
    	
    	//get train graph data from System.in and load to graph structure
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Please enter Graph of trains to analyze");
    	edgeToken = scan.findInLine(edgePattern);  
    	do { 
    		start = Character.toString(edgeToken.charAt(0));
    		end = Character.toString(edgeToken.charAt(1));
    		weight = Integer.parseInt(Character.toString(edgeToken.charAt(2)));
    		trainRoutes.addEdge(start, end, weight);
    		edgeToken = scan.findInLine(edgePattern);  
    	}while(edgeToken!=null);
        
    	scan.close();
    	
    	//tests 1-5
    	ArrayList<String[]> testCases = new ArrayList<String[]>();
    	testCases.add(new String[]{"A", "B", "C"});
    	testCases.add(new String[]{"A", "D"});
    	testCases.add(new String[]{"A", "D", "C"});
    	testCases.add(new String[]{"A", "E", "B", "C", "D"});
    	testCases.add(new String[]{"A", "E", "D"});
    	
    	int distance = 0;
    	String response;
    	for (int i = 0; i < testCases.size(); i++){
    		distance = trainRoutes.getDistanceForRoute(testCases.get(i));
    		if (distance == DirectedGraph.DISTANCE_ERROR_CASE)
    			response = "#" + (i + 1) + ": "+ ERROR_CASE;
    		else
    			response =  "#" + (i + 1) + ": "+ distance;
    		
    		System.out.println(response);
    	}
    	
    	int t = trainRoutes.getNumberOfRoutesWithMaxStops("C", "C", 3);
    	System.out.println("#6: " + t);
    	
    	t = trainRoutes.getNumberOfRoutesWithExactStops("A", "C", 4);
    	System.out.println("#7: " + t);
    	
    	t = trainRoutes.getShortestDistance("A", "C");
    	System.out.println("#8: " + t);
    	
    	t = trainRoutes.getShortestDistance("B", "B");
    	System.out.println("#9: " + t);
    	
    	t = trainRoutes.getNumberOfRoutesWithLengthLessThan("C", "C", 30);
    	System.out.println("#10: " + t);
    }
}
