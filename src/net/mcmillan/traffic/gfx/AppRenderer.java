package net.mcmillan.traffic.gfx;

import java.awt.Color;
import java.util.ArrayList;

import net.mcmillan.traffic.debug.DebugOptions;
import net.mcmillan.traffic.simulation.AppLogic;
import net.mcmillan.traffic.simulation.Graph.Edge;
import net.mcmillan.traffic.simulation.Graph.Node;
import net.mcmillan.traffic.simulation.tools.Tool;

// Manage all rendering of the highway and traffic simulation, seperate from the control panel.
public class AppRenderer {
	
	private AppLogic scene; // content
	private RenderableCanvas target; // destination
	private CameraGraphics cameraGfx = new CameraGraphics(); // camera
	private long delta = 0;
	
	public AppRenderer() {
		addMonitor(new LabeledMonitorable("Camera", () -> "[" + cameraGfx.camX() + ", " + cameraGfx.camY() + "]")); // Make the visibility of these toggable in Control Panel
		addMonitor(new LabeledMonitorable("Viewport", () -> "[" + target.getWidth() + ", " + target.getHeight() + "]"));
		addMonitor(new LabeledMonitorable("Ticks", () -> Long.toString(scene.ticks())));
		addMonitor(new LabeledMonitorable("Delta", () -> Long.toString(delta)));
		addMonitor(new LabeledMonitorable("Nodes", () -> Integer.toString(scene.graph.nodes.size())));
		addMonitor(new LabeledMonitorable("Edges", () -> Integer.toString(scene.graph.edges.size())));
	}
	
	// Scene, target, camera assignment
	public void setScene(AppLogic sim) {
		scene = sim;
		cameraGfx.setCamera(scene.getCamera());
		if (scene != null && target != null) {
			target.setEventQueue(scene.getEventQueue());
		}
	}
	public void setTarget(RenderableCanvas canvas) {
		target = canvas; // TODO: Add listener for canvas size change
		if (scene != null && target != null) {
			target.setEventQueue(scene.getEventQueue());
		}
	}
	public void setCamera(Camera c) {
		cameraGfx.setCamera(c);
	}
	
	// Drawing
	public void draw(long d) {
		cameraGfx.setGraphics(target.getGraphics());
		delta = d;
		intl_draw(cameraGfx, delta);
		target.showBuffer();
	}
	private void intl_draw(CameraGraphics cg, long delta) {
		drawBackground(cg, scene!=null?scene.debugOptions.get(DebugOptions.DRAW_GRIDLINES):false); // Background/Grid
		if (scene != null) drawScene(cg); // Simulation
		
		// Draw tool
		Tool t = scene.getTool();
		if (t != null) t.draw(cg, scene);
		
		if (scene.debugOptions.get(DebugOptions.DRAW_MONITORABLES)) drawMonitorables(cg, delta);
	}
	private static final Color background = new Color(0,0,0), gridLines = new Color(45,45,45);
	private static final int gridSize = 32;
	private void drawBackground(CameraGraphics cg, boolean drawGridlines) {
		int w = target.getWidth(), h = target.getHeight();
		int lbound = cg.camX(), rbound = lbound + w, tbound = cg.camY(), bbound = tbound + h;
		cg.setColor(background);
		cg.fillOverlayRect(0, 0, w, h);
		if (!drawGridlines) return;
		cg.setColor(gridLines);
		for (int x=(lbound/gridSize)*gridSize;x<rbound;x+=gridSize) cg.drawLine(x,tbound,x,bbound);
		for (int y=(tbound/gridSize)*gridSize;y<bbound;y+=gridSize) cg.drawLine(lbound,y,rbound,y);
	}
	// Drawing the graph
	private void drawScene(CameraGraphics cg) {
		cg.setColor(Color.white);
		for (Node n : scene.graph.nodes) cg.drawOval(n.transform);
		cg.setColor(Color.cyan);
		for (Edge e : scene.graph.edges) cg.drawLine(e.a.cx(),e.a.cy(),e.b.cx(),e.b.cy());
		cg.setColor(Color.GREEN);
		for (Node n : scene.graph.nodes) cg.drawCenteredString(n.getLabel(), n.cx(), n.cy());
		cg.setColor(Color.RED);
		for (Edge e : scene.graph.edges) cg.drawCenteredString(e.getLabel(), e.cx(), e.cy());
	}
	
	// Monitorables, meant to easily metrics related to the simulation
	private ArrayList<Monitorable> monitors = new ArrayList<>();
	private void drawMonitorables(CameraGraphics cg, long delta) {
		cg.setColor(Color.white);
		int x = 2, y = 1, yf = 12;
		for (Monitorable m : monitors) cg.drawOverlayString(m.get(), x, y++*yf);
	}
	public void addMonitor(Monitorable m) { monitors.add(m); }
	public boolean removeMonitor(Monitorable m) { return monitors.remove(m); }
	public interface Monitorable { public String get(); }
	public class LabeledMonitorable implements Monitorable {
		private String l, s = ": "; // Label + separator
		private Monitorable m; // Data retriever
		public LabeledMonitorable(String label, Monitorable mon) {
			l = label; 
			m = mon;
		}
		public LabeledMonitorable(String label, String seperator, Monitorable mon) {
			l = label; 
			s = seperator;
			m = mon;
		}
		@Override public String get() { return l + s + m.get(); }
	}
}
