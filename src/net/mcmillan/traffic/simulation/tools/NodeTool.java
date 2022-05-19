package net.mcmillan.traffic.simulation.tools;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.gfx.CameraGraphics;
import net.mcmillan.traffic.math.IVec2;
import net.mcmillan.traffic.simulation.AppLogic;

public class NodeTool implements Tool {

	public static final String NAME = "Node";
	@Override
	public String getName() { return NAME; }

	@Override
	public boolean mousePressed(Event e, AppLogic sim) { return false; }

	@Override
	public boolean mouseReleased(Event e, AppLogic sim) { return false; }

	@Override
	public boolean mouseClicked(Event e, AppLogic sim) {
		switch (e.button()) {
		case Event.BUTTON1:
			Camera c = sim.getCamera();
			try {
				sim.graph.addNodeAt(IVec2.make(e.x()+c.x, e.y()+c.y), sim.nextNodeLabel.get());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseDragged(Event e, AppLogic sim) { return false; }

	@Override
	public void draw(CameraGraphics cg, AppLogic sim) { }

}
