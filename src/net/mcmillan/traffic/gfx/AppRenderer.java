package net.mcmillan.traffic.gfx;

import java.awt.Color;
import java.util.ArrayList;

import net.mcmillan.traffic.debug.DebugOptions;
import net.mcmillan.traffic.math.ITransform2D;
import net.mcmillan.traffic.simulation.AppLogic;

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
		// Draw mouse selection rect
		if (scene.getDragMode() == AppLogic.DRAG_SELECT_MODE) {
			cg.setColor(Color.white);
			ITransform2D selection = scene.getSelectionTransform();
			cg.drawRect(selection);
		}
		
		drawMonitorables(cg, delta);
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
	// Drawing the highway
	private void drawScene(CameraGraphics cg) {
		// TODO: Reimpl app rendering
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
