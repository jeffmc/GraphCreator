package net.mcmillan.traffic.simulation.tools;

import java.awt.Color;

import net.mcmillan.traffic.event.Event;
import net.mcmillan.traffic.gfx.Camera;
import net.mcmillan.traffic.gfx.CameraGraphics;
import net.mcmillan.traffic.math.IVec2;
import net.mcmillan.traffic.simulation.AppLogic;
import net.mcmillan.traffic.simulation.Graph;

public class EdgeTool implements Tool {

	public static final String NAME = "Edge";
	@Override public String getName() { return NAME; }

	private Graph.Node fn; // First node
	public boolean isActive() { return fn != null; }
	public Graph.Node getStartNode() { return fn; }
	public int tx = -1, ty = -1;
	
	@Override
	public boolean mousePressed(Event e, AppLogic sim) {
		switch (e.button()) {
		case Event.BUTTON1:
			Camera c = sim.getCamera();
			tx = e.x()+c.x;
			ty = e.y()+c.y;
			fn = sim.graph.findNodeAt(IVec2.make(tx, ty));
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseReleased(Event e, AppLogic sim) {
		switch (e.button()) {
		case Event.BUTTON1:
			if (fn == null) return true;
			Camera c = sim.getCamera();
			tx = e.x()+c.x;
			ty = e.y()+c.y;
			Graph.Node sn = sim.graph.findNodeAt(IVec2.make(tx,ty));
			if (sn == null) {
				fn = null;
				return true;
			}
			try {
				sim.graph.addEdge(fn, sn, sim.nextEdgeLabel.get());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			fn = null;
			return true;
		}
		fn = null;
		return false;
	}
	@Override
	public boolean mouseClicked(Event e, AppLogic sim) { return false; }
	@Override
	public boolean mouseDragged(Event e, AppLogic sim) { 
		if (isActive()) {
			Camera c = sim.getCamera();
			tx = e.x()+c.x;
			ty = e.y()+c.y;
			return true; 
		}
		return false;
	}
	@Override
	public void draw(CameraGraphics cg, AppLogic sim) { 
		if (isActive()) {
			Graph.Node tn = sim.graph.findNodeAt(IVec2.make(tx,ty));
			if (tn != null) {
				cg.setColor(Color.YELLOW);
				cg.drawLine(fn.cx(), fn.cy(), tn.cx(), tn.cy());
			} else {
				cg.setColor(Color.RED);
				cg.drawLine(fn.cx(), fn.cy(), tx, ty);
			}	
		}
	}

}
