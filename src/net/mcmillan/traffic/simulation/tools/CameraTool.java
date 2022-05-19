package net.mcmillan.traffic.simulation.tools;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.gfx.CameraGraphics;
import net.mcmillan.traffic.simulation.AppLogic;

public class CameraTool implements Tool {

	public static final String NAME = "Camera(SHOULDN'T BE EXPOSED IN MENU)";
	@Override
	public String getName() { return NAME; }
	private boolean active = false;
	private int cox, coy, msx, msy; // Cam original x,y / Mouse drag start x,y
	
	@Override
	public boolean mousePressed(Event e, AppLogic sim) { 
		switch (e.button()) {
		case Event.BUTTON2:
			active = true;
			Camera c = sim.getCamera();
			cox = c.x;
			coy = c.y;
			msx = e.x();
			msy = e.y();
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseReleased(Event e, AppLogic sim) {
		switch (e.button()) {
		case Event.BUTTON2:
			active = false;
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseDragged(Event e, AppLogic sim) {
		if (active) {
			Camera c = sim.getCamera();
			c.x = cox + msx - e.x();
			c.y = coy + msy - e.y();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseClicked(Event e, AppLogic sim) {
		return false;
	}
	
	@Override
	public void draw(CameraGraphics cg, AppLogic sim) { }


}
