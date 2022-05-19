package net.mcmillan.traffic.simulation.tools;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.gfx.CameraGraphics;
import net.mcmillan.traffic.simulation.AppLogic;

public interface Tool {

	public String getName();
	
	public boolean mousePressed(Event e, AppLogic sim);
	public boolean mouseReleased(Event e, AppLogic sim);
	public boolean mouseClicked(Event e, AppLogic sim);
	public boolean mouseDragged(Event e, AppLogic sim);
	
	public void draw(CameraGraphics cg, AppLogic sim);
	
}
