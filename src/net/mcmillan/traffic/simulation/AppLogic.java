package net.mcmillan.traffic.simulation;

import net.mcmillan.traffic.debug.ControlPanel;
import net.mcmillan.traffic.debug.DebugOptions;
import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.event.EventQueue;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.simulation.tools.CameraTool;
import net.mcmillan.traffic.simulation.tools.EdgeTool;
import net.mcmillan.traffic.simulation.tools.NodeTool;
import net.mcmillan.traffic.simulation.tools.SelectTool;
import net.mcmillan.traffic.simulation.tools.Tool;

// Class meant to handle and organize all logic of the application
public class AppLogic {
	
	// Data/Scene
	public Graph graph = new Graph();
	
	// Label setting
	public ControlPanel.LabelFactory nextNodeLabel, nextEdgeLabel;
	
	// Tools
	public static final Tool[] TOOLS = new Tool[] {
		new NodeTool(),	
		new EdgeTool(),	
		new SelectTool(),	
	};
	private Tool activeTool = null;
	public void setTool(Tool t) { activeTool = t; }
	public Tool getTool() { return activeTool; }
	
	// Subsystems
	private EventQueue eventq = new EventQueue();
	public EventQueue getEventQueue() { return eventq; }
	private Camera cam = new Camera();
	public Camera getCamera() { return cam; }
	public DebugOptions debugOptions = new DebugOptions();
	
	// State management
	private boolean running = false;
	public boolean isRunning() { return running; }
	public void start() {
		if (running) throw new IllegalStateException("Can't start an already active simulation!");
		running = true;
	}
	public void stop() {
		if (!running) throw new IllegalStateException("Can't stop inactive simulation!");
		running = false;
	}
	
	// Ticking
	private long ticks = 0;
	public long ticks() { return ticks; }
	public void tick(long delta) {
		if (!running) throw new IllegalStateException("Can't tick inactive simulation!");
		pollEvents(); // Run this before any update code.
		update(delta);
		ticks++;
	}
	
	public void update(long delta) { }

	// Tools
//	private Tool camTool = new CameraTool(); // Overlay tool that is handled before other typical tools
	
	private Tool[] overlayTools = new Tool[] {
		new CameraTool(),	
	};
	
	public void pollEvents() {
		eventq.unload();
		while (!eventq.unloadedEmpty()) {
			Event e = eventq.pop();
			boolean overlayHandled = false;
			switch (e.code) {
			case Event.KEY_RELEASED:
				switch (e.keyCode()) {
					// TODO: Key handlers
				}
				break;
				
			case Event.MOUSE_PRESSED:
				overlayHandled = false;
				for (Tool t : overlayTools) {
					overlayHandled |= t.mousePressed(e, this);
					if (overlayHandled) break;
				}
				if (overlayHandled) break;
				if (activeTool != null) activeTool.mousePressed(e, this);
				break;
				
			case Event.MOUSE_CLICKED:
				overlayHandled = false;
				for (Tool t : overlayTools) {
					overlayHandled |= t.mouseClicked(e, this);
					if (overlayHandled) break;
				}
				if (overlayHandled) break;
				if (activeTool != null) activeTool.mouseClicked(e, this);
				break;
				
			case Event.MOUSE_RELEASED:
				overlayHandled = false;
				for (Tool t : overlayTools) {
					overlayHandled |= t.mouseReleased(e, this);
					if (overlayHandled) break;
				}
				if (overlayHandled) break;
				if (activeTool != null) activeTool.mouseReleased(e, this);
				break;
				
			case Event.MOUSE_DRAGGED:
				overlayHandled = false;
				for (Tool t : overlayTools) {
					overlayHandled |= t.mouseDragged(e, this);
					if (overlayHandled) break;
				}
				if (overlayHandled) break;
				if (activeTool != null) activeTool.mouseDragged(e, this);
				break;
				
			case Event.MOUSE_WHEEL_MOVED:
				break;
			}
		}
	}

}
