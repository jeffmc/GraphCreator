package net.mcmillan.traffic.simulation;

import java.util.ArrayList;

import net.mcmillan.traffic.math.ITransform2D;
import net.mcmillan.traffic.math.IVec2;

public class Graph {

	public ArrayList<Node> nodes = new ArrayList<Node>();
	public void addNodeAt(IVec2 v, String label) { 
		Node n = new Node(v, label);
		for (Node o : nodes)
			if (n.transform.intersects(o.transform))
				throw new IllegalStateException("Node intersects existing node");
		nodes.add(n); 
	}
	
	public Node nodeAt(IVec2 v) {
		for (Node n : nodes) 
			if (n.transform.contains(v)) return n;
		return null;
	}
	
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public void addEdge(Node a, Node b, String label) {
		Edge e = new Edge(a,b,label);
		if (edges.contains(e)) {
			throw new IllegalStateException("Edge already exists");
		} else {
			edges.add(e);
		}
	}
	
	public class Edge {
		public Node a,b;
		private String label;
		public String getLabel() { return label; }
		public Edge(Node a, Node b, String l) {
			if (a == null) throw new IllegalArgumentException("'A' endpoint cannot be null");
			if (b == null) throw new IllegalArgumentException("'B' endpoint cannot be null");
			if (a.equals(b)) throw new IllegalArgumentException("Edge's endpoints cannot be the same node");
			this.a = a;
			this.b = b;
			this.label = l;
		}
		public Node opposite(Node t) {
			if (t == null) throw new IllegalArgumentException("Node is null");
			if (t == a) {
				return b;
			} else if (t == b) {
				return a;
			}
			throw new IllegalArgumentException("Node isn't one of two endpoints on this edge");
		}
		public boolean equals(Object o) {
			if (o instanceof Edge) {
				Edge n = (Edge) o;
				return (n.a==b&&n.b==a) || (n.a==a&&n.b==b);
			} else {
				return false;
			}
		}
		@Override
		public int hashCode() {
			return a.hashCode() ^ b.hashCode();
		}
		public int cx() { return (a.cx() + b.cx())/2; }
		public int cy() { return (a.cy() + b.cy())/2; }
	}
	public class Node {
		private String label;
		public String getLabel() { return label; }
		public ITransform2D transform;
		private static final int NODE_RADIUS = 15, NODE_DIAMETER = NODE_RADIUS * 2;
		public Node(IVec2 p, String label) {
			transform = new ITransform2D(p.x()-NODE_RADIUS, p.y()-NODE_RADIUS, NODE_DIAMETER, NODE_DIAMETER);
			this.label = label;
		}
		public int cx() { return transform.cx(); }
		public int cy() { return transform.cy(); }
	}
}
