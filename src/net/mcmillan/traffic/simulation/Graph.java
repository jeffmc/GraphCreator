package net.mcmillan.traffic.simulation;

import java.util.ArrayList;

import net.mcmillan.traffic.math.ITransform2D;
import net.mcmillan.traffic.math.IVec2;

public class Graph {

	private static final int NODE_RADIUS = 15, NODE_DIAMETER = NODE_RADIUS * 2;
	
	public ArrayList<ITransform2D> nodes = new ArrayList<ITransform2D>();
	public void addNodeAt(IVec2 n) { nodes.add(new ITransform2D(n.x()-NODE_RADIUS, n.y()-NODE_RADIUS, NODE_DIAMETER, NODE_DIAMETER)); }
	
	public ITransform2D nodeAt(IVec2 v) {
		for (ITransform2D n : nodes) 
			if (n.contains(v)) return n;
		return null;
	}
	
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public void addEdge(ITransform2D a, ITransform2D b) {
		edges.add(new Edge(a,b));
	}
	
	public class Edge {
		public ITransform2D a,b;
		public Edge(ITransform2D a, ITransform2D b) {
			this.a = a;
			this.b = b;
		}
	}
}
