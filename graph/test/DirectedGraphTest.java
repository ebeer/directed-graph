package com.ebeer.graph.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.ebeer.graph.DirectedGraph;

public class DirectedGraphTest {

	@Test
	public void testAddEdge() {
		DirectedGraph graph = new DirectedGraph();
		graph.addEdge("a", "b", 1);
		assertEquals(graph.getSize(), 2);
		System.out.println(graph.toString());
	}

	
}
