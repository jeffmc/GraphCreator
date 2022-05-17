package net.mcmillan.traffic.simulation;

import net.mcmillan.traffic.debug.DebugOptions;
import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.event.EventQueue;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.math.ITransform2D;
import net.mcmillan.traffic.math.IVec2;

// Class meant to handle and organize all logic of the application
public class AppLogic {
	
	// Subsystems
	private EventQueue eventq = new EventQueue();
	public EventQueue getEventQueue() { return eventq; }
	private Camera cam = new Camera();
	public Camera getCamera() { return cam; }
	public DebugOptions debugOptions = new DebugOptions();
	
	// State management
	private boolean running = false;
	private boolean paused = true;
	private boolean stepOnce = false;
	public void togglePlayPause() { paused = !paused; }
	public void step() { stepOnce = true; }
	
	public boolean isRunning() { return running; }
	public boolean isPaused() { return paused; }
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
		if ((!paused) || stepOnce) {
			update(delta);
			ticks++;
			stepOnce = false;
		}
	}
	public void update(long delta) {
		
	}

	// Dragging TODO: Replace drag mode with more sophisticated Drag System
	private int dragMode = -1;
	public int getDragMode() { return dragMode; }
	public static final int DRAG_SELECT_MODE = 1, DRAG_CAM_MODE = 0;
	public IVec2 mstart = IVec2.make(), mnow = IVec2.make(), msize = IVec2.make(), morigin = IVec2.make();
	private int cox, coy, msx, msy; // Cam original x,y / Mouse drag start x,y TODO: Place these variables into DragHandler system

	public void pollEvents() {
		eventq.unload();
		while (!eventq.unloadedEmpty()) {
			Event e = eventq.pop();
			switch (e.code) {
			case Event.KEY_RELEASED:
				switch (e.keyCode()) {
				
				}
				break;
			case Event.MOUSE_PRESSED:
				switch (e.button()) {
				case Event.BUTTON1:
					dragMode = DRAG_SELECT_MODE;
					setMouseNowRelativeToCam(e);
					mstart.set(mnow);
					break;
				case Event.BUTTON2:
					dragMode = DRAG_CAM_MODE;
					cox = cam.x;
					coy = cam.y;
					msx = e.x();
					msy = e.y();
					break;
				}
				break;
			case Event.MOUSE_RELEASED:
				switch (e.button()) {
				case Event.BUTTON1:
					selectMouseArea();
					break;
				}
				dragMode = -1;
				break;
			case Event.MOUSE_DRAGGED:
				switch (dragMode) {
				case DRAG_SELECT_MODE:
					setMouseNowRelativeToCam(e);
					break;
				case DRAG_CAM_MODE:
					cam.x = cox + msx - e.x();
					cam.y = coy + msy - e.y();
					break;
				}
				break;
			case Event.MOUSE_WHEEL_MOVED:
				break;
			}
		}
		msize.set(mstart).sub(mnow).abs();
		morigin.set(mstart).min(mnow);
	}

	private void setMouseNowRelativeToCam(Event e) { // Converts from screen -> world coords
		mnow.set(e.x()+cam.x, e.y()+cam.y);
	}
	
	private void selectMouseArea() {
		ITransform2D t = getSelectionTransform();
		System.out.println("[AppLogic] Select mouse area: [" + String.join(",", 
				Integer.toString(t.x()), Integer.toString(t.y()), 
				Integer.toString(t.w()), Integer.toString(t.h())) + "]");
	}
	
	public ITransform2D getSelectionTransform() {
		return new ITransform2D(morigin, msize);
	}

}
